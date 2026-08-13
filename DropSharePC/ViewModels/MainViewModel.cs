using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using DropShare.Desktop.Models;
using DropShare.Desktop.Services;

namespace DropShare.Desktop.ViewModels
{
    public class MainViewModel : INotifyPropertyChanged
    {
        private readonly KesifServisi _kesifServisi;
        private readonly AktarimServisi _aktarimServisi;

        public ObservableCollection<CihazModeli> Cihazlar { get; } = new();
        public ObservableCollection<AktarimGecmisOgesi> Gecmis { get; } = new();

        private CihazModeli? _secilenCihaz;
        public CihazModeli? SecilenCihaz
        {
            get => _secilenCihaz;
            set
            {
                _secilenCihaz = value;
                OnPropertyChanged();
                OnPropertyChanged(nameof(SecilenCihazMetni));
                OnPropertyChanged(nameof(CihazSeciliMi));
            }
        }

        public string SecilenCihazMetni => SecilenCihaz != null
            ? $"{SecilenCihaz.CihazSimgesi} {SecilenCihaz.CihazAdi} ({SecilenCihaz.IpAdresi})"
            : "⚠️ Lütfen sol listeden hedef bir cihaz seçin";

        public bool CihazSeciliMi => SecilenCihaz != null;

        private string _ipAdresiMetni = "";
        public string IpAdresiMetni
        {
            get => _ipAdresiMetni;
            set { _ipAdresiMetni = value; OnPropertyChanged(); }
        }

        private string _pcCihazAdi = "";
        public string PcCihazAdi
        {
            get => _pcCihazAdi;
            set { _pcCihazAdi = value; OnPropertyChanged(); }
        }

        private bool _otomatikKabul = false;
        public bool OtomatikKabul
        {
            get => _otomatikKabul;
            set
            {
                _otomatikKabul = value;
                _aktarimServisi.OtomatikKabul = value;
                OnPropertyChanged();
            }
        }

        private bool _taraniyor = false;
        public bool Taraniyor
        {
            get => _taraniyor;
            set { _taraniyor = value; OnPropertyChanged(); }
        }

        private double _aktarimYuzde = 0;
        public double AktarimYuzde
        {
            get => _aktarimYuzde;
            set { _aktarimYuzde = value; OnPropertyChanged(); }
        }

        private string _aktarimDurumMetni = "Hazır - Dosya göndermek veya almak için bir cihaz seçin.";
        public string AktarimDurumMetni
        {
            get => _aktarimDurumMetni;
            set { _aktarimDurumMetni = value; OnPropertyChanged(); }
        }

        private string _aktarimHizMetni = "";
        public string AktarimHizMetni
        {
            get => _aktarimHizMetni;
            set { _aktarimHizMetni = value; OnPropertyChanged(); }
        }

        private string _gonderilecekMetin = "";
        public string GonderilecekMetin
        {
            get => _gonderilecekMetin;
            set { _gonderilecekMetin = value; OnPropertyChanged(); }
        }

        private string _manuelIp = "";
        public string ManuelIp
        {
            get => _manuelIp;
            set { _manuelIp = value; OnPropertyChanged(); }
        }

        public MainViewModel()
        {
            _kesifServisi = new KesifServisi();
            _aktarimServisi = new AktarimServisi();

            IpAdresiMetni = $"IP: {AagYardimcisi.BirincilIpGetir()}";
            PcCihazAdi = $"Cihaz: {_kesifServisi.CihazAdi}";

            _kesifServisi.CihazlarGuncellendi += OnCihazlarGuncellendi;
            _aktarimServisi.GelenDosyaIstegiAlindi += OnGelenDosyaIstegi;
            _aktarimServisi.GelenMetinAlindi += OnGelenMetin;
            _aktarimServisi.AktarimIlerledi += OnAktarimIlerledi;
            _aktarimServisi.AktarimTamamlandi += OnAktarimTamamlandi;

            _kesifServisi.Baslat();
            _aktarimServisi.SunucuyuBaslat();
        }

        public void Durdur()
        {
            _kesifServisi.Durdur();
            _aktarimServisi.SunucuyuDurdur();
        }

        private void OnCihazlarGuncellendi(System.Collections.Generic.List<CihazModeli> cihazlar)
        {
            Application.Current?.Dispatcher.Invoke(() =>
            {
                Cihazlar.Clear();
                foreach (var c in cihazlar)
                {
                    Cihazlar.Add(c);
                }

                if (SecilenCihaz != null)
                {
                    var guncel = Cihazlar.FirstOrDefault(x => x.CihazId == SecilenCihaz.CihazId);
                    if (guncel != null) SecilenCihaz = guncel;
                }
                else if (Cihazlar.Count > 0)
                {
                    SecilenCihaz = Cihazlar[0];
                }
            });
        }

        public async Task AgiTekrarTaraAsync()
        {
            Taraniyor = true;
            AktarimDurumMetni = "🔍 Ağdaki DropShare cihazları taranıyor...";
            await _kesifServisi.YayinPingGonderAsync();
            await _kesifServisi.AltAgiTaraAsync();
            await Task.Delay(1500);
            Taraniyor = false;
            AktarimDurumMetni = Cihazlar.Count > 0 ? $"✅ {Cihazlar.Count} cihaz bulundu." : "⚠️ Cihaz bulunamadı. Lütfen telefondaki DropShare uygulamasının açık olduğundan emin olun.";
        }

        public void ManuelIpEkle()
        {
            if (string.IsNullOrWhiteSpace(ManuelIp)) return;
            _kesifServisi.ManuelCihazEkle(ManuelIp);
            ManuelIp = "";
        }

        public async Task DosyaGonderAsync(string dosyaYolu)
        {
            if (SecilenCihaz == null)
            {
                MessageBox.Show("Lütfen dosyayı göndermek istediğiniz bir hedef cihaz seçin.", "Cihaz Seçilmedi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            var fileInfo = new FileInfo(dosyaYolu);
            if (!fileInfo.Exists) return;

            AktarimDurumMetni = $"🚀 Gönderiliyor: {fileInfo.Name}";
            AktarimYuzde = 0;

            DateTime startTime = DateTime.UtcNow;
            long lastBytes = 0;

            var gecmis = await _aktarimServisi.DosyaGonderAsync(SecilenCihaz.IpAdresi, dosyaYolu, _kesifServisi.CihazAdi, (gonderilen, toplam) =>
            {
                Application.Current?.Dispatcher.Invoke(() =>
                {
                    double yuzde = toplam > 0 ? (double)gonderilen / toplam * 100 : 0;
                    AktarimYuzde = yuzde;

                    var now = DateTime.UtcNow;
                    double elapsed = (now - startTime).TotalSeconds;
                    if (elapsed >= 0.2)
                    {
                        double bytesSec = (gonderilen - lastBytes) / Math.Max(0.1, elapsed);
                        long kalanBayt = toplam - gonderilen;
                        long etaSec = bytesSec > 0 ? (long)(kalanBayt / bytesSec) : 0;

                        AktarimHizMetni = $"%{yuzde:F0} - {AagYardimcisi.HizFormatla(bytesSec)} - Kalan: {AagYardimcisi.SureFormatla(etaSec)}";
                        startTime = now;
                        lastBytes = gonderilen;
                    }
                });
            });

            Application.Current?.Dispatcher.Invoke(() =>
            {
                Gecmis.Insert(0, gecmis);
                if (gecmis.Durum == AktarimDurumu.TAMAMLANDI)
                {
                    AktarimYuzde = 100;
                    AktarimDurumMetni = $"✅ {fileInfo.Name} başarıyla gönderildi!";
                    AktarimHizMetni = "Tamamlandı";
                }
                else
                {
                    AktarimDurumMetni = $"❌ Hata: {gecmis.HataMesaji}";
                    AktarimHizMetni = "";
                }
            });
        }

        public async Task MetinGonderAsync()
        {
            if (string.IsNullOrWhiteSpace(GonderilecekMetin)) return;
            if (SecilenCihaz == null)
            {
                MessageBox.Show("Lütfen metni göndermek istediğiniz bir cihaz seçin.", "Cihaz Seçilmedi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            bool ok = await _aktarimServisi.MetinGonderAsync(SecilenCihaz.IpAdresi, GonderilecekMetin, _kesifServisi.CihazAdi);
            if (ok)
            {
                AktarimDurumMetni = "✅ Metin/Link başarıyla gönderildi!";
                GonderilecekMetin = "";
            }
            else
            {
                AktarimDurumMetni = "❌ Metin gönderilemedi. Cihazın açık ve aynı ağda olduğundan emin olun.";
            }
        }

        private void OnGelenDosyaIstegi(GelenDosyaIstegi istek, Func<bool, Task> cevap)
        {
            Application.Current?.Dispatcher.Invoke(async () =>
            {
                string msg = $"📱 {istek.GonderenCihazAdi} cihazından gelen dosya:\n\n📄 {istek.DosyaAdi}\n📏 Boyut: {AagYardimcisi.BaytFormatla(istek.DosyaBoyutu)}\n\nKabul ediyor musunuz?";
                var res = MessageBox.Show(msg, "Gelen Dosya İsteği", MessageBoxButton.YesNo, MessageBoxImage.Question);
                await cevap(res == MessageBoxResult.Yes);
            });
        }

        private void OnGelenMetin(string gonderen, string metin)
        {
            Application.Current?.Dispatcher.Invoke(() =>
            {
                try
                {
                    Clipboard.SetText(metin);
                    MessageBox.Show($"📱 {gonderen} cihazından gelen metin panoya kopyalandı:\n\n{metin}", "Gelen Metin", MessageBoxButton.OK, MessageBoxImage.Information);
                }
                catch { }
            });
        }

        private void OnAktarimIlerledi(AktarimGecmisOgesi oge)
        {
            Application.Current?.Dispatcher.Invoke(() =>
            {
                AktarimDurumMetni = $"⬇️ İndiriliyor: {oge.DosyaAdi}";
            });
        }

        private void OnAktarimTamamlandi(AktarimGecmisOgesi oge)
        {
            Application.Current?.Dispatcher.Invoke(() =>
            {
                Gecmis.Insert(0, oge);
                if (oge.Durum == AktarimDurumu.TAMAMLANDI)
                {
                    AktarimYuzde = 100;
                    AktarimDurumMetni = $"✅ {oge.DosyaAdi} başarıyla alındı!";
                    AktarimHizMetni = $"İndirilenler klasörüne kaydedildi: {oge.TamYol}";
                }
                else
                {
                    AktarimDurumMetni = $"❌ Hata: {oge.HataMesaji}";
                }
            });
        }

        public void IndirmeKlasorunuAc()
        {
            try
            {
                Process.Start("explorer.exe", _aktarimServisi.IndirmeKlasoru);
            }
            catch { }
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string? propName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propName));
        }
    }
}
