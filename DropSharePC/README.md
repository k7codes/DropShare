# 🚀 DropShare Desktop (Windows PC Application)

DropShare Desktop, Android telefon, tablet ve Windows PC cihazları arasında local Wi-Fi / LAN ağında sıfır konfigürasyon ile yüksek hızlı, güvenli ve tamamen çevrimdışı dosya ve metin aktarımı sağlayan **.NET 8 WPF** masaüstü uygulamasıdır.

---

## ⚡ Neden Yeniden Geliştirildi ve Düzeltildi?

1. **UDP Ağ Keşif Sorunu Çözüldü**:
   - `255.255.255.255` yayın adresi Windows Güvenlik Duvarı veya sanal ağ kartlarında (WSL, Hyper-V, VMware) engellenebildiği için, uygulama artık sistemdeki **tüm aktif ağ arayüzlerini ve alt ağ yayın adreslerini (örn: `192.168.1.255`)** otomatik tespit eder.
   - Ayrıca "Ağı Tara" butonuna basıldığında local `/24` alt ağına **akıllı paralel IP taraması** yaparak Android telefonları anında tespit eder.

2. **TCP Dosya Akışı Tampon Bellek (Buffer) Bozulması Çözüldü**:
   - Eski versiyonda kullanılan `StreamReader` sınıfı, JSON başlık satırını okurken TCP soketindeki sonraki ham dosya baytlarını kendi dahili tampon belleğine çektiği için dosyalar bozuluyor veya aktarım duruyordu.
   - Yeni versiyonda özel ham bayt satır okuyucu (`ReadLineRawAsync`) geliştirildi. Sıfır veri kaybı ile GB'larca büyüklükteki dosyalar kesintisiz aktarılır.

3. **Gelişmiş MVVM Mimarisi ve Modern UI**:
   - Material Design 3 renk paleti, canlı hız göstergesi (MB/s veya KB/s), kalan süre (ETA) tahmini, geçmiş günlüğü ve panodan tek tıkla metin/URL paylaşımı eklendi.

---

## 🛠️ Derleme ve Çalıştırma

### Gereksinimler
- **Windows 10 / 11**
- **.NET 8.0 SDK** (veya Visual Studio 2022)

### 1. Komut Satırı (.NET CLI) ile Çalıştırma
```bash
cd DropShare.Desktop
dotnet run
```

### 2. Tek Başına Çalışan .EXE (Single-File Executable) Üretme
Tüm bağımlılıkları tek bir `DropShareDesktop.exe` dosyasına paketlemek için:
```bash
cd DropShare.Desktop
dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
```
Derlenen dosya `DropShare.Desktop/bin/Release/net8.0-windows/win-x64/publish/` dizininde hazır olacaktır.

---

## 📡 Ağ Protokolü Bilgileri

- **UDP Keşif Portu**: `52525` (JSON Broadcast & Subnet Ping)
- **TCP Aktarım Portu**: `52526` (JSON Başlık + Ham Bayt Akışı)
- ** SHA-256 Veri Bütünlüğü**: Dosyalar indirildikten sonra hash doğrulamasından geçirilir.
