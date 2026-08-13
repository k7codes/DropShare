using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using DropShare.Desktop.Models;

namespace DropShare.Desktop.Services
{
    public class KesifServisi
    {
        public const int DISCOVERY_PORT = 52525;
        public const int TRANSFER_PORT = 52526;

        private UdpClient? _udpListener;
        private readonly ConcurrentDictionary<string, CihazModeli> _bulunanCihazlar = new();
        private CancellationTokenSource? _cts;

        public event Action<List<CihazModeli>>? CihazlarGuncellendi;

        public string CihazAdi { get; set; } = Environment.MachineName;
        public string CihazId { get; set; } = "pc_" + Guid.NewGuid().ToString().Substring(0, 8);

        public void Baslat()
        {
            Durdur();

            _cts = new CancellationTokenSource();

            try
            {
                _udpListener = new UdpClient();
                _udpListener.Client.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
                _udpListener.Client.Bind(new IPEndPoint(IPAddress.Any, DISCOVERY_PORT));
                _udpListener.EnableBroadcast = true;

                Task.Run(() => DinlemeyeBasla(_cts.Token));
                Task.Run(() => PeriyodikPingGonder(_cts.Token));
                Task.Run(() => TemizlikDongusu(_cts.Token));

                // İlk başlatmada hızlı tespitle alt ağı tara
                Task.Run(() => AltAgiTaraAsync());
            }
            catch { }
        }

        public void Durdur()
        {
            _cts?.Cancel();
            try
            {
                _udpListener?.Close();
                _udpListener?.Dispose();
            }
            catch { }
            _udpListener = null;
        }

        private async Task DinlemeyeBasla(CancellationToken token)
        {
            while (!token.IsCancellationRequested && _udpListener != null)
            {
                try
                {
                    var result = await _udpListener.ReceiveAsync(token);
                    string json = Encoding.UTF8.GetString(result.Buffer);

                    if (json.Contains("DropShare"))
                    {
                        ParseKesifMesaji(json, result.RemoteEndPoint.Address.ToString());
                    }
                }
                catch (OperationCanceledException) { break; }
                catch { }
            }
        }

        private void ParseKesifMesaji(string json, string gonderenIp)
        {
            try
            {
                // Bilgisayarın kendi IP adreslerini filtrele (Kendi kendine dosya göndermeyi engelle)
                var localIps = AagYardimcisi.TumAktifAagArayuzleriniGetir().Select(a => a.IpAdresi).ToList();
                if (localIps.Contains(gonderenIp) || gonderenIp == "127.0.0.1" || gonderenIp == "::1") return;

                using var doc = JsonDocument.Parse(json);
                var root = doc.RootElement;

                string id = root.TryGetProperty("cihazId", out var pId) ? pId.GetString() ?? "" : "";
                if (string.IsNullOrEmpty(id) || id == CihazId) return;

                string adi = root.TryGetProperty("cihazAdi", out var pAdi) ? pAdi.GetString() ?? "Bilinmeyen Cihaz" : "Bilinmeyen Cihaz";
                string turStr = root.TryGetProperty("cihazTuru", out var pTur) ? pTur.GetString() ?? "TELEFON" : "TELEFON";
                int port = root.TryGetProperty("port", out var pPort) ? pPort.GetInt32() : TRANSFER_PORT;

                CihazTuru tur = turStr.ToUpper() switch
                {
                    "BILGISAYAR" or "DESKTOP" or "PC" or "WINDOWS" => CihazTuru.BILGISAYAR,
                    "TABLET" => CihazTuru.TABLET,
                    "WEB" => CihazTuru.WEB,
                    _ => CihazTuru.TELEFON
                };

                var cihaz = new CihazModeli
                {
                    CihazId = id,
                    CihazAdi = adi,
                    CihazTuru = tur,
                    IpAdresi = gonderenIp,
                    Port = port,
                    SonGorulmeMs = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
                };

                _bulunanCihazlar[id] = cihaz;
                CihazlarGuncellendi?.Invoke(_bulunanCihazlar.Values.OrderByDescending(c => c.SonGorulmeMs).ToList());
            }
            catch { }
        }

        public async Task YayinPingGonderAsync()
        {
            try
            {
                var arayuzler = AagYardimcisi.TumAktifAagArayuzleriniGetir();
                foreach (var aag in arayuzler)
                {
                    var pingObj = new KesifPaketi
                    {
                        Protokol = "DropShare",
                        Tip = "PING",
                        CihazId = CihazId,
                        CihazAdi = CihazAdi,
                        CihazTuru = "BILGISAYAR",
                        IpAdresi = aag.IpAdresi,
                        Port = TRANSFER_PORT,
                        Zaman = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
                    };

                    string json = JsonSerializer.Serialize(pingObj);
                    byte[] bytes = Encoding.UTF8.GetBytes(json);

                    using var sender = new UdpClient();
                    sender.EnableBroadcast = true;

                    // 1) Genel Yayın (255.255.255.255)
                    try
                    {
                        await sender.SendAsync(bytes, bytes.Length, new IPEndPoint(IPAddress.Broadcast, DISCOVERY_PORT));
                    }
                    catch { }

                    // 2) Arayüze Özel Yayın (örn: 192.168.1.255)
                    if (!string.IsNullOrEmpty(aag.YayınAdresi) && IPAddress.TryParse(aag.YayınAdresi, out var targetBcast))
                    {
                        try
                        {
                            await sender.SendAsync(bytes, bytes.Length, new IPEndPoint(targetBcast, DISCOVERY_PORT));
                        }
                        catch { }
                    }
                }
            }
            catch { }
        }

        public async Task AltAgiTaraAsync()
        {
            // Alt ağdaki tüm IP'lere doğrudan UDP PING at (192.168.1.1 -> 192.168.1.254)
            try
            {
                var arayuzler = AagYardimcisi.TumAktifAagArayuzleriniGetir();
                var tasks = new List<Task>();

                foreach (var aag in arayuzler)
                {
                    if (aag.IpAdresi.StartsWith("127.")) continue;

                    string[] parts = aag.IpAdresi.Split('.');
                    if (parts.Length != 4) continue;

                    string subnetPrefix = $"{parts[0]}.{parts[1]}.{parts[2]}.";
                    int ownLast = int.Parse(parts[3]);

                    var pingObj = new KesifPaketi
                    {
                        Protokol = "DropShare",
                        Tip = "PING",
                        CihazId = CihazId,
                        CihazAdi = CihazAdi,
                        CihazTuru = "BILGISAYAR",
                        IpAdresi = aag.IpAdresi,
                        Port = TRANSFER_PORT,
                        Zaman = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
                    };

                    string json = JsonSerializer.Serialize(pingObj);
                    byte[] bytes = Encoding.UTF8.GetBytes(json);

                    // 1 ile 254 arasındaki tüm IP'lere paralel PING paketleri gönder
                    for (int i = 1; i < 255; i++)
                    {
                        if (i == ownLast) continue;
                        string targetIpStr = subnetPrefix + i;

                        tasks.Add(Task.Run(async () =>
                        {
                            try
                            {
                                using var sender = new UdpClient();
                                if (IPAddress.TryParse(targetIpStr, out var targetIp))
                                {
                                    await sender.SendAsync(bytes, bytes.Length, new IPEndPoint(targetIp, DISCOVERY_PORT));
                                }
                            }
                            catch { }
                        }));
                    }
                }

                await Task.WhenAll(tasks);
            }
            catch { }
        }

        private async Task PeriyodikPingGonder(CancellationToken token)
        {
            while (!token.IsCancellationRequested)
            {
                await YayinPingGonderAsync();
                await Task.Delay(3000, token);
            }
        }

        private async Task TemizlikDongusu(CancellationToken token)
        {
            while (!token.IsCancellationRequested)
            {
                await Task.Delay(5000, token);
                long simdi = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();

                var silinecekler = _bulunanCihazlar
                    .Where(kvp => (simdi - kvp.Value.SonGorulmeMs) > 20000)
                    .Select(kvp => kvp.Key)
                    .ToList();

                foreach (var key in silinecekler)
                {
                    _bulunanCihazlar.TryRemove(key, out _);
                }

                if (silinecekler.Count > 0)
                {
                    CihazlarGuncellendi?.Invoke(_bulunanCihazlar.Values.OrderByDescending(c => c.SonGorulmeMs).ToList());
                }
            }
        }

        public void ManuelCihazEkle(string ip, string cihazAdi = "")
        {
            string cleanIp = ip.Trim();
            if (string.IsNullOrEmpty(cleanIp)) return;

            // Kendi IP adresimizi eklemeyi engelle
            var localIps = AagYardimcisi.TumAktifAagArayuzleriniGetir().Select(a => a.IpAdresi).ToList();
            if (localIps.Contains(cleanIp) || cleanIp == "127.0.0.1") return;

            string id = "manual_" + cleanIp.Replace(".", "_");
            var cihaz = new CihazModeli
            {
                CihazId = id,
                CihazAdi = string.IsNullOrEmpty(cihazAdi) ? $"Mobil Cihaz ({cleanIp})" : cihazAdi,
                CihazTuru = CihazTuru.TELEFON,
                IpAdresi = cleanIp,
                Port = TRANSFER_PORT,
                SonGorulmeMs = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
            };

            _bulunanCihazlar[id] = cihaz;
            CihazlarGuncellendi?.Invoke(_bulunanCihazlar.Values.OrderByDescending(c => c.SonGorulmeMs).ToList());

            // Doğrudan hedef telefona UDP PING paketi gönder
            Task.Run(async () =>
            {
                try
                {
                    var pingObj = new KesifPaketi
                    {
                        Protokol = "DropShare",
                        Tip = "PING",
                        CihazId = CihazId,
                        CihazAdi = CihazAdi,
                        CihazTuru = "BILGISAYAR",
                        IpAdresi = AagYardimcisi.BirincilIpGetir(),
                        Port = TRANSFER_PORT,
                        Zaman = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
                    };

                    string json = JsonSerializer.Serialize(pingObj);
                    byte[] bytes = Encoding.UTF8.GetBytes(json);

                    using var sender = new UdpClient();
                    if (IPAddress.TryParse(cleanIp, out var targetIp))
                    {
                        await sender.SendAsync(bytes, bytes.Length, new IPEndPoint(targetIp, DISCOVERY_PORT));
                    }
                }
                catch { }
            });
        }
    }
}
