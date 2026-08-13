using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using DropShare.Desktop.Models;

namespace DropShare.Desktop.Services
{
    public class AktarimServisi
    {
        public const int PORT = 52526;
        private TcpListener? _listener;
        private CancellationTokenSource? _cts;

        public event Action<GelenDosyaIstegi, Func<bool, Task>>? GelenDosyaIstegiAlindi;
        public event Action<string, string>? GelenMetinAlindi; // (GonderenCihaz, Metin)
        public event Action<AktarimGecmisOgesi>? AktarimIlerledi;
        public event Action<AktarimGecmisOgesi>? AktarimTamamlandi;

        public bool OtomatikKabul { get; set; } = false;
        public string IndirmeKlasoru { get; set; }

        public AktarimServisi()
        {
            IndirmeKlasoru = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Downloads", "DropShare");
            if (!Directory.Exists(IndirmeKlasoru))
            {
                Directory.CreateDirectory(IndirmeKlasoru);
            }
        }

        public void SunucuyuBaslat()
        {
            SunucuyuDurdur();

            _cts = new CancellationTokenSource();
            try
            {
                _listener = new TcpListener(IPAddress.Any, PORT);
                _listener.Server.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
                _listener.Start();

                Task.Run(() => BaglantilariDinle(_cts.Token));
            }
            catch { }
        }

        public void SunucuyuDurdur()
        {
            _cts?.Cancel();
            try
            {
                _listener?.Stop();
            }
            catch { }
            _listener = null;
        }

        private async Task BaglantilariDinle(CancellationToken token)
        {
            while (!token.IsCancellationRequested && _listener != null)
            {
                try
                {
                    var client = await _listener.AcceptTcpClientAsync(token);
                    _ = Task.Run(() => GelenBaglantiyiIsleAsync(client, token));
                }
                catch (OperationCanceledException) { break; }
                catch { break; }
            }
        }

        private async Task GelenBaglantiyiIsleAsync(TcpClient client, CancellationToken token)
        {
            using (client)
            using (var stream = client.GetStream())
            {
                // Raw line reading to avoid StreamReader buffer corruption
                string? headerLine = await ReadLineRawAsync(stream, token);
                if (string.IsNullOrEmpty(headerLine)) return;

                try
                {
                    using var doc = JsonDocument.Parse(headerLine);
                    string tip = doc.RootElement.GetProperty("tip").GetString() ?? "";

                    if (tip == "DOSYA_ISTEGI")
                    {
                        var istek = JsonSerializer.Deserialize<GelenDosyaIstegi>(headerLine);
                        if (istek == null) return;

                        istek.GonderenIp = (client.Client.RemoteEndPoint as IPEndPoint)?.Address.ToString() ?? "";

                        bool kabulEdildi = OtomatikKabul;
                        if (!OtomatikKabul && GelenDosyaIstegiAlindi != null)
                        {
                            var tcs = new TaskCompletionSource<bool>();
                            GelenDosyaIstegiAlindi.Invoke(istek, async (kabul) =>
                            {
                                tcs.TrySetResult(kabul);
                                await Task.CompletedTask;
                            });

                            // Max 60 sn bekle
                            var timeoutTask = Task.Delay(60000, token);
                            var completedTask = await Task.WhenAny(tcs.Task, timeoutTask);
                            if (completedTask == tcs.Task)
                            {
                                kabulEdildi = await tcs.Task;
                            }
                            else
                            {
                                kabulEdildi = false;
                            }
                        }

                        if (kabulEdildi)
                        {
                            byte[] responseBytes = Encoding.UTF8.GetBytes("{\"durum\":\"KABUL\"}\n");
                            await stream.WriteAsync(responseBytes, 0, responseBytes.Length, token);
                            await stream.FlushAsync(token);

                            await GelenDosyaVerisiniAlAsync(stream, istek, token);
                        }
                        else
                        {
                            byte[] responseBytes = Encoding.UTF8.GetBytes("{\"durum\":\"RED\"}\n");
                            await stream.WriteAsync(responseBytes, 0, responseBytes.Length, token);
                            await stream.FlushAsync(token);
                        }
                    }
                    else if (tip == "METIN_PAYLASIMI")
                    {
                        var metinPaket = JsonSerializer.Deserialize<MetinPaylasimPaketi>(headerLine);
                        if (metinPaket != null)
                        {
                            GelenMetinAlindi?.Invoke(metinPaket.GonderenCihazAdi, metinPaket.MetinIcerigi);
                            byte[] responseBytes = Encoding.UTF8.GetBytes("{\"durum\":\"TAMAM\"}\n");
                            await stream.WriteAsync(responseBytes, 0, responseBytes.Length, token);
                        }
                    }
                }
                catch { }
            }
        }

        private async Task GelenDosyaVerisiniAlAsync(Stream stream, GelenDosyaIstegi istek, CancellationToken token)
        {
            if (!Directory.Exists(IndirmeKlasoru))
            {
                Directory.CreateDirectory(IndirmeKlasoru);
            }

            string dosyaAdi = istek.DosyaAdi;
            string kayitYolu = Path.Combine(IndirmeKlasoru, dosyaAdi);

            int sayac = 1;
            while (File.Exists(kayitYolu))
            {
                string nameWithoutExt = Path.GetFileNameWithoutExtension(dosyaAdi);
                string ext = Path.GetExtension(dosyaAdi);
                kayitYolu = Path.Combine(IndirmeKlasoru, $"{nameWithoutExt} ({sayac}){ext}");
                sayac++;
            }

            long toplam = istek.DosyaBoyutu;
            long okunan = 0;
            byte[] buffer = new byte[64 * 1024];

            var gecmisOgesi = new AktarimGecmisOgesi
            {
                DosyaAdi = Path.GetFileName(kayitYolu),
                TamYol = kayitYolu,
                DosyaBoyutu = toplam,
                CihazAdi = istek.GonderenCihazAdi,
                IsGelen = true,
                Zaman = DateTime.Now,
                Durum = AktarimDurumu.AKTARILIYOR
            };

            AktarimIlerledi?.Invoke(gecmisOgesi);

            using var sha256 = SHA256.Create();
            using var fileStream = new FileStream(kayitYolu, FileMode.Create, FileAccess.Write, FileShare.None, 64 * 1024, true);

            var lastReport = DateTime.UtcNow;
            long lastReportBytes = 0;

            try
            {
                while (okunan < toplam)
                {
                    int kalan = (int)Math.Min(buffer.Length, toplam - okunan);
                    int read = await stream.ReadAsync(buffer, 0, kalan, token);
                    if (read <= 0) break;

                    await fileStream.WriteAsync(buffer, 0, read, token);
                    sha256.TransformBlock(buffer, 0, read, null, 0);

                    okunan += read;

                    var now = DateTime.UtcNow;
                    if ((now - lastReport).TotalSeconds >= 0.3 || okunan == toplam)
                    {
                        lastReport = now;
                        lastReportBytes = okunan;
                        AktarimIlerledi?.Invoke(gecmisOgesi);
                    }
                }

                fileStream.Flush();
                sha256.TransformFinalBlock(Array.Empty<byte>(), 0, 0);
                string hesaplananHash = Convert.ToHexString(sha256.Hash ?? Array.Empty<byte>()).ToLower();

                bool hashOk = string.IsNullOrEmpty(istek.Sha256Hash) || istek.Sha256Hash.Equals(hesaplananHash, StringComparison.OrdinalIgnoreCase);

                if (okunan >= toplam && hashOk)
                {
                    gecmisOgesi.Durum = AktarimDurumu.TAMAMLANDI;
                }
                else
                {
                    gecmisOgesi.Durum = AktarimDurumu.HATA;
                    gecmisOgesi.HataMesaji = !hashOk ? "SHA-256 Hash Doğrulaması Başarısız" : "Eksik Dosya Aktarımı";
                }
            }
            catch (Exception ex)
            {
                gecmisOgesi.Durum = AktarimDurumu.HATA;
                gecmisOgesi.HataMesaji = ex.Message;
            }

            AktarimTamamlandi?.Invoke(gecmisOgesi);
        }

        public async Task<AktarimGecmisOgesi> DosyaGonderAsync(string targetIp, string dosyaYolu, string kendiCihazAdi, Action<long, long>? progressCallback)
        {
            var fileInfo = new FileInfo(dosyaYolu);
            var gecmis = new AktarimGecmisOgesi
            {
                DosyaAdi = fileInfo.Name,
                TamYol = dosyaYolu,
                DosyaBoyutu = fileInfo.Length,
                CihazAdi = targetIp,
                IsGelen = false,
                Zaman = DateTime.Now,
                Durum = AktarimDurumu.ONAY_BEKLIYOR
            };

            if (!fileInfo.Exists)
            {
                gecmis.Durum = AktarimDurumu.HATA;
                gecmis.HataMesaji = "Dosya bulunamadı";
                return gecmis;
            }

            // Small files SHA256 calculation
            string hash = "";
            if (fileInfo.Length < 100 * 1024 * 1024)
            {
                try
                {
                    using var sha256 = SHA256.Create();
                    using var fs = fileInfo.OpenRead();
                    byte[] hashBytes = await sha256.ComputeHashAsync(fs);
                    hash = Convert.ToHexString(hashBytes).ToLower();
                }
                catch { }
            }

            try
            {
                using var client = new TcpClient();
                await client.ConnectAsync(targetIp, PORT);
                using var stream = client.GetStream();

                var istek = new GelenDosyaIstegi
                {
                    Tip = "DOSYA_ISTEGI",
                    IstekId = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds().ToString(),
                    GonderenCihazAdi = kendiCihazAdi,
                    DosyaAdi = fileInfo.Name,
                    DosyaBoyutu = fileInfo.Length,
                    DosyaSayisi = 1,
                    Sha256Hash = hash
                };

                string jsonHeader = JsonSerializer.Serialize(istek) + "\n";
                byte[] headerBytes = Encoding.UTF8.GetBytes(jsonHeader);
                await stream.WriteAsync(headerBytes, 0, headerBytes.Length);
                await stream.FlushAsync();

                // Raw line reading for answer
                string? answerLine = await ReadLineRawAsync(stream, CancellationToken.None);
                if (string.IsNullOrEmpty(answerLine))
                {
                    gecmis.Durum = AktarimDurumu.HATA;
                    gecmis.HataMesaji = "Karşı cihaz yanıt vermedi";
                    return gecmis;
                }

                using var doc = JsonDocument.Parse(answerLine);
                string durum = doc.RootElement.GetProperty("durum").GetString() ?? "";

                if (durum == "KABUL")
                {
                    gecmis.Durum = AktarimDurumu.AKTARILIYOR;
                    byte[] buffer = new byte[64 * 1024];
                    long gonderilen = 0;
                    long toplam = fileInfo.Length;

                    using var sourceFs = fileInfo.OpenRead();
                    int read;
                    var lastTime = DateTime.UtcNow;

                    while ((read = await sourceFs.ReadAsync(buffer, 0, buffer.Length)) > 0)
                    {
                        await stream.WriteAsync(buffer, 0, read);
                        gonderilen += read;

                        var now = DateTime.UtcNow;
                        if ((now - lastTime).TotalSeconds >= 0.3 || gonderilen == toplam)
                        {
                            lastTime = now;
                            progressCallback?.Invoke(gonderilen, toplam);
                        }
                    }

                    await stream.FlushAsync();
                    gecmis.Durum = AktarimDurumu.TAMAMLANDI;
                }
                else
                {
                    gecmis.Durum = AktarimDurumu.REDDEDILDI;
                    gecmis.HataMesaji = "Karşı cihaz aktarımı reddetti";
                }
            }
            catch (Exception ex)
            {
                gecmis.Durum = AktarimDurumu.HATA;
                gecmis.HataMesaji = ex.Message;
            }

            return gecmis;
        }

        public async Task<bool> MetinGonderAsync(string targetIp, string metin, string kendiCihazAdi)
        {
            try
            {
                using var client = new TcpClient();
                await client.ConnectAsync(targetIp, PORT);
                using var stream = client.GetStream();

                var paket = new MetinPaylasimPaketi
                {
                    Tip = "METIN_PAYLASIMI",
                    GonderenCihazAdi = kendiCihazAdi,
                    MetinIcerigi = metin
                };

                string json = JsonSerializer.Serialize(paket) + "\n";
                byte[] bytes = Encoding.UTF8.GetBytes(json);
                await stream.WriteAsync(bytes, 0, bytes.Length);
                await stream.FlushAsync();
                return true;
            }
            catch
            {
                return false;
            }
        }

        // Custom Helper: Read bytes up to newline '\n' without StreamReader buffer corruption
        private static async Task<string?> ReadLineRawAsync(Stream stream, CancellationToken token)
        {
            using var ms = new MemoryStream();
            byte[] buffer = new byte[1];
            while (true)
            {
                int read = await stream.ReadAsync(buffer, 0, 1, token);
                if (read == 0) break;
                byte b = buffer[0];
                if (b == (byte)'\n') break;
                if (b != (byte)'\r') ms.WriteByte(b);
            }
            if (ms.Length == 0) return null;
            return Encoding.UTF8.GetString(ms.ToArray());
        }
    }
}
