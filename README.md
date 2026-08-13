# DropShare

<p align="center">
  <img src="https://i.imgur.com/INXlgpI.png" alt="DropShare Logo" width="128">
</p>

<h1 align="center">DropShare</h1>

<p align="center">
  <strong>Fast. Local. Private.</strong>
</p>

<p align="center">
  Android ve Windows cihazlar arasında hızlı, güvenli ve doğrudan dosya paylaşımı.
</p>

<p align="center">
  <a href="https://github.com/k7codes/DropShare/releases">
    <img src="https://img.shields.io/github/v/release/k7codes/DropShare?style=for-the-badge&label=Release&color=5865F2" alt="Release">
  </a>
  <a href="https://github.com/k7codes/DropShare/stargazers">
    <img src="https://img.shields.io/github/stars/k7codes/DropShare?style=for-the-badge&logo=github&label=Stars&color=F5C518" alt="Stars">
  </a>
  <a href="https://github.com/k7codes/DropShare/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/k7codes/DropShare?style=for-the-badge&label=License&color=22C55E" alt="License">
  </a>
  <a href="https://github.com/k7codes/DropShare">
    <img src="https://img.shields.io/github/last-commit/k7codes/DropShare?style=for-the-badge&label=Updated&color=06B6D4" alt="Last Commit">
  </a>
</p>

<p align="center">
  <a href="#özellikler">Özellikler</a>
  &nbsp;•&nbsp;
  <a href="#nasıl-çalışır">Nasıl Çalışır</a>
  &nbsp;•&nbsp;
  <a href="#platformlar">Platformlar</a>
  &nbsp;•&nbsp;
  <a href="#kurulum">Kurulum</a>
  &nbsp;•&nbsp;
  <a href="#teknolojiler">Teknolojiler</a>
  &nbsp;•&nbsp;
  <a href="#geliştirme">Geliştirme</a>
</p>

---

## Genel Bakış

DropShare, aynı yerel ağ üzerinde bulunan cihazlar arasında hızlı ve doğrudan dosya paylaşımı için geliştirilmiş modern bir dosya transfer uygulamasıdır.

Android ve Windows cihazları tek bir deneyim altında birleştirerek dosya, klasör, bağlantı, metin ve pano içeriklerinin cihazlar arasında kolayca paylaşılmasını sağlar.

DropShare'ın temel yaklaşımı:

> **Dosyanızı seçin. Cihazınızı seçin. Gönderin.**

Dosyaları üçüncü taraf bulut servislerine yüklemek yerine yerel ağ iletişimine odaklanarak hızlı ve pratik bir aktarım deneyimi sunmayı amaçlar.

---

## Özellikler

<table>
<tr>
<td width="50%">

### Dosya Transferi

- Tekli dosya aktarımı
- Çoklu dosya aktarımı
- Klasör aktarımı
- Büyük dosya desteği
- Fotoğraf ve video aktarımı
- Belge aktarımı
- Arşiv dosyaları
- Sürükle ve bırak
- Gerçek zamanlı ilerleme
- Transfer geçmişi

</td>
<td width="50%">

### Cihaz Yönetimi

- Yerel ağ cihaz keşfi
- Android ↔ Windows
- Android ↔ Android
- Windows ↔ Windows
- QR kod ile eşleştirme
- Eşleştirme kodu
- Transfer onayı
- Cihaz adı yapılandırması
- Bağlantı durumu

</td>
</tr>

<tr>
<td width="50%">

### Veri Paylaşımı

- Pano paylaşımı
- Metin paylaşımı
- Bağlantı paylaşımı
- Dosya paylaşımı
- Klasör paylaşımı
- Transfer geçmişi

</td>
<td width="50%">

### Kullanıcı Deneyimi

- Modern arayüz
- Sade kullanım
- Gerçek zamanlı transfer durumu
- Gönderme ve alma işlemleri
- Yapılandırılabilir ayarlar
- Android ve Windows desteği

</td>
</tr>
</table>

---

## Nasıl Çalışır?

DropShare, cihazların aynı yerel ağ üzerinde bulunmasını temel alır.
┌──────────────────────┐
│ DropShare │
│ Yerel Ağ │
└──────────┬───────────┘
│
┌────────────────┼────────────────┐
│ │ │
▼ ▼ ▼
┌─────────┐ ┌─────────┐ ┌─────────┐
│ Android │ │ Windows │ │ Android │
└────┬────┘ └────┬────┘ └────┬────┘
│ │ │
└────────────────┼────────────────┘
│
▼
Doğrudan Transfer

text

### Transfer Akışı

1. **Dosya Seçimi**
2. **Cihaz Keşfi**
3. **Hedef Cihaz**
4. **Eşleştirme**
5. **Transfer Onayı**
6. **Dosya Aktarımı**
7. **İlerleme Takibi**
8. **Tamamlandı**

### QR ile Eşleştirme

DropShare, cihazların hızlı şekilde eşleştirilmesi için QR tabanlı bağlantı mekanizması kullanır.
┌───────────────────┐
│ Windows │
│ │
│ QR Oluştur │
│ │
│ ┌─────────┐ │
│ │ QR CODE │ │
│ └─────────┘ │
└─────────┬─────────┘
│
│ Tara
▼
┌───────────────────┐
│ Android │
│ │
│ QR Tara │
│ │
│ Eşleştirildi │
└─────────┬─────────┘
│
▼
Dosya Transferi

text

QR eşleştirme, kullanıcıların bağlantı bilgilerini manuel olarak girmesine gerek kalmadan cihazları hızlı şekilde birbirine bağlamasını sağlar.

---

## Platformlar

| Platform | Durum |
|----------|-------|
| Android  | Aktif |
| Windows  | Aktif |

### Android

Android istemcisi mobil cihazlar için tasarlanmıştır.

- Yakındaki cihazları keşfetme
- Dosya seçme
- Çoklu dosya seçme
- Dosya gönderme
- Dosya alma
- Transfer onayı
- Transfer geçmişi
- Cihaz adı
- İndirme konumu
- Pano paylaşımı
- Bağlantı paylaşımı
- Tema ayarları

### Windows

Windows istemcisi masaüstü kullanımına odaklanır.

- Dosya gönderme
- Dosya alma
- Çoklu dosya aktarımı
- Klasör aktarımı
- Sürükle ve bırak
- Cihaz keşfi
- QR eşleştirme
- Transfer ilerlemesi
- Transfer geçmişi
- Pano paylaşımı
- Bağlantı paylaşımı

### Transfer Geçmişi

Gerçekleştirilen transferler geçmiş üzerinden takip edilebilir.
┌────────────────────────────────────────────────────────────┐
│ Transfer Geçmişi │
├──────────────────┬──────────┬─────────────────┬─────────────┤
│ Dosya │ Boyut │ Yön │ Durum │
├──────────────────┼──────────┼─────────────────┼─────────────┤
│ project.zip │ 384 MB │ Windows → Phone │ Tamamlandı │
│ video.mp4 │ 1.42 GB │ Phone → Windows │ Tamamlandı │
│ documents.zip │ 82 MB │ Windows → Phone │ Tamamlandı │
└──────────────────┴──────────┴─────────────────┴─────────────┘

text

### Pano ve Bağlantı Paylaşımı

Dosya aktarımının yanında metin ve bağlantılar da cihazlar arasında paylaşılabilir.
Android
│
│ https://github.com/k7codes/DropShare
▼
Windows
│
├── Aç
└── Kopyala

text

---

## Güvenlik

DropShare, transfer işlemlerini kullanıcı kontrolünde tutacak şekilde tasarlanmıştır.

Temel yaklaşım:

- Transfer isteklerinin kullanıcı tarafından onaylanması
- Hedef cihazın kullanıcı tarafından seçilmesi
- Cihaz eşleştirme
- Yerel ağ üzerinden iletişim
- Kontrollü transfer oturumları
- Bilinmeyen cihazlardan gelen isteklerin kontrol edilmesi

**Not:** Yerel ağ kullanımı tek başına güvenlik garantisi değildir. Güvenilmeyen veya ortak kullanılan ağlarda dosya transferi gerçekleştirilirken ağ güvenliği ayrıca değerlendirilmelidir.

---

## Gereksinimler

### Windows
- Windows 10 veya üzeri
- Yerel ağ bağlantısı
- Gerekli ağ izinleri
- Gerekli dosya sistemi izinleri

### Android
- Android cihaz
- Wi-Fi bağlantısı
- Gerekli ağ izinleri
- Gerekli dosya erişim izinleri

---

## Kurulum

### Depoyu Klonlama

```bash
git clone https://github.com/k7codes/DropShare.git
cd DropShare
Android
Projeyi Android Studio ile açın.

Gradle bağımlılıklarının yüklenmesini bekledikten sonra uygulamayı fiziksel Android cihazınızda veya emülatörde çalıştırabilirsiniz.

Debug APK oluşturmak için:

bash
./gradlew assembleDebug
Windows üzerinde:

bash
.\gradlew.bat assembleDebug
APK çıktısı genellikle:

text
app/build/outputs/apk/debug/app-debug.apk
konumunda oluşturulur.

Windows
Bağımlılıkları yükleyin:

bash
dotnet restore
Projeyi derleyin:

bash
dotnet build
Release sürümü:

bash
dotnet publish -c Release
Tek dosyalı Windows çıktısı:

bash
dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
Proje Yapısı
text
DropShare/
│
├── DropShareMobile/
│   ├── app/
│   │   └── src/
│   ├── gradle/
│   ├── build.gradle
│   └── settings.gradle
│
├── DropSharePC/
│   ├── Properties/
│   ├── Resources/
│   ├── Models/
│   ├── Services/
│   ├── Network/
│   ├── Transfer/
│   └── UI/
│
├── .gitignore
├── .gitattributes
├── LICENSE
└── README.md
Teknolojiler
Android
Kotlin

Android SDK

Gradle

Windows
C#

.NET

Windows SDK

Ağ Gereksinimleri
Cihazların birbirleriyle iletişim kurabilmesi için aynı yerel ağ üzerinde bulunmaları gerekir.

Aşağıdaki durumlar cihaz keşfini veya transfer işlemlerini etkileyebilir:

Farklı Wi-Fi ağları

AP Isolation

Client Isolation

Misafir ağları

Windows Güvenlik Duvarı

VPN yazılımları

Engellenmiş bağlantı noktaları

Ağ yönlendirme sorunları

Sanal ağ adaptörleri

En sağlıklı kullanım için Android ve Windows cihazlar

Bu yanıt, yalnızca referans amaçlı AI tarafından oluşturulmuştur.
kral direkt kopyalayabileyim tüm herşeyi kod içinde ver Developed By K7~
İşte düzenlenmiş ve tamamlanmış README.md dosyası. Developed by K7~ eklendi:

markdown
# DropShare

<p align="center">
  <img src="https://i.imgur.com/INXlgpI.png" alt="DropShare Logo" width="128">
</p>

<h1 align="center">DropShare</h1>

<p align="center">
  <strong>Fast. Local. Private.</strong>
</p>

<p align="center">
  Android ve Windows cihazlar arasında hızlı, güvenli ve doğrudan dosya paylaşımı.
</p>

<p align="center">
  <a href="https://github.com/k7codes/DropShare/releases">
    <img src="https://img.shields.io/github/v/release/k7codes/DropShare?style=for-the-badge&label=Release&color=5865F2" alt="Release">
  </a>
  <a href="https://github.com/k7codes/DropShare/stargazers">
    <img src="https://img.shields.io/github/stars/k7codes/DropShare?style=for-the-badge&logo=github&label=Stars&color=F5C518" alt="Stars">
  </a>
  <a href="https://github.com/k7codes/DropShare/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/k7codes/DropShare?style=for-the-badge&label=License&color=22C55E" alt="License">
  </a>
  <a href="https://github.com/k7codes/DropShare">
    <img src="https://img.shields.io/github/last-commit/k7codes/DropShare?style=for-the-badge&label=Updated&color=06B6D4" alt="Last Commit">
  </a>
</p>

<p align="center">
  <a href="#özellikler">Özellikler</a>
  &nbsp;•&nbsp;
  <a href="#nasıl-çalışır">Nasıl Çalışır</a>
  &nbsp;•&nbsp;
  <a href="#platformlar">Platformlar</a>
  &nbsp;•&nbsp;
  <a href="#kurulum">Kurulum</a>
  &nbsp;•&nbsp;
  <a href="#teknolojiler">Teknolojiler</a>
  &nbsp;•&nbsp;
  <a href="#geliştirme">Geliştirme</a>
</p>

---

## Genel Bakış

DropShare, aynı yerel ağ üzerinde bulunan cihazlar arasında hızlı ve doğrudan dosya paylaşımı için geliştirilmiş modern bir dosya transfer uygulamasıdır.

Android ve Windows cihazları tek bir deneyim altında birleştirerek dosya, klasör, bağlantı, metin ve pano içeriklerinin cihazlar arasında kolayca paylaşılmasını sağlar.

DropShare'ın temel yaklaşımı:

> **Dosyanızı seçin. Cihazınızı seçin. Gönderin.**

Dosyaları üçüncü taraf bulut servislerine yüklemek yerine yerel ağ iletişimine odaklanarak hızlı ve pratik bir aktarım deneyimi sunmayı amaçlar.

---

## Özellikler

<table>
<tr>
<td width="50%">

### Dosya Transferi

- Tekli dosya aktarımı
- Çoklu dosya aktarımı
- Klasör aktarımı
- Büyük dosya desteği
- Fotoğraf ve video aktarımı
- Belge aktarımı
- Arşiv dosyaları
- Sürükle ve bırak
- Gerçek zamanlı ilerleme
- Transfer geçmişi

</td>
<td width="50%">

### Cihaz Yönetimi

- Yerel ağ cihaz keşfi
- Android ↔ Windows
- Android ↔ Android
- Windows ↔ Windows
- QR kod ile eşleştirme
- Eşleştirme kodu
- Transfer onayı
- Cihaz adı yapılandırması
- Bağlantı durumu

</td>
</tr>

<tr>
<td width="50%">

### Veri Paylaşımı

- Pano paylaşımı
- Metin paylaşımı
- Bağlantı paylaşımı
- Dosya paylaşımı
- Klasör paylaşımı
- Transfer geçmişi

</td>
<td width="50%">

### Kullanıcı Deneyimi

- Modern arayüz
- Sade kullanım
- Gerçek zamanlı transfer durumu
- Gönderme ve alma işlemleri
- Yapılandırılabilir ayarlar
- Android ve Windows desteği

</td>
</tr>
</table>

---

## Nasıl Çalışır?

DropShare, cihazların aynı yerel ağ üzerinde bulunmasını temel alır.
┌──────────────────────┐
│ DropShare │
│ Yerel Ağ │
└──────────┬───────────┘
│
┌────────────────┼────────────────┐
│ │ │
▼ ▼ ▼
┌─────────┐ ┌─────────┐ ┌─────────┐
│ Android │ │ Windows │ │ Android │
└────┬────┘ └────┬────┘ └────┬────┘
│ │ │
└────────────────┼────────────────┘
│
▼
Doğrudan Transfer

text

### Transfer Akışı
Dosya Seçimi
│
▼
Cihaz Keşfi
│
▼
Hedef Cihaz
│
▼
Eşleştirme
│
▼
Transfer Onayı
│
▼
Dosya Aktarımı
│
▼
İlerleme Takibi
│
▼
Tamamlandı

text

### QR ile Eşleştirme

DropShare, cihazların hızlı şekilde eşleştirilmesi için QR tabanlı bağlantı mekanizması kullanır.
┌───────────────────┐
│ Windows │
│ │
│ QR Oluştur │
│ │
│ ┌─────────┐ │
│ │ QR CODE │ │
│ └─────────┘ │
└─────────┬─────────┘
│
│ Tara
▼
┌───────────────────┐
│ Android │
│ │
│ QR Tara │
│ │
│ Eşleştirildi │
└─────────┬─────────┘
│
▼
Dosya Transferi

text

QR eşleştirme, kullanıcıların bağlantı bilgilerini manuel olarak girmesine gerek kalmadan cihazları hızlı şekilde birbirine bağlamasını sağlar.

---

## Platformlar

| Platform | Durum |
|----------|-------|
| Android  | Aktif |
| Windows  | Aktif |

### Android

Android istemcisi mobil cihazlar için tasarlanmıştır.

- Yakındaki cihazları keşfetme
- Dosya seçme
- Çoklu dosya seçme
- Dosya gönderme
- Dosya alma
- Transfer onayı
- Transfer geçmişi
- Cihaz adı
- İndirme konumu
- Pano paylaşımı
- Bağlantı paylaşımı
- Tema ayarları

### Windows

Windows istemcisi masaüstü kullanımına odaklanır.

- Dosya gönderme
- Dosya alma
- Çoklu dosya aktarımı
- Klasör aktarımı
- Sürükle ve bırak
- Cihaz keşfi
- QR eşleştirme
- Transfer ilerlemesi
- Transfer geçmişi
- Pano paylaşımı
- Bağlantı paylaşımı

### Transfer Geçmişi

Gerçekleştirilen transferler geçmiş üzerinden takip edilebilir.
┌────────────────────────────────────────────────────────────┐
│ Transfer Geçmişi │
├──────────────────┬──────────┬─────────────────┬─────────────┤
│ Dosya │ Boyut │ Yön │ Durum │
├──────────────────┼──────────┼─────────────────┼─────────────┤
│ project.zip │ 384 MB │ Windows → Phone │ Tamamlandı │
│ video.mp4 │ 1.42 GB │ Phone → Windows │ Tamamlandı │
│ documents.zip │ 82 MB │ Windows → Phone │ Tamamlandı │
└──────────────────┴──────────┴─────────────────┴─────────────┘

text

### Pano ve Bağlantı Paylaşımı

Dosya aktarımının yanında metin ve bağlantılar da cihazlar arasında paylaşılabilir.
Android
│
│ https://github.com/k7codes/DropShare
▼
Windows
│
├── Aç
└── Kopyala

text

---

## Güvenlik

DropShare, transfer işlemlerini kullanıcı kontrolünde tutacak şekilde tasarlanmıştır.

Temel yaklaşım:

- Transfer isteklerinin kullanıcı tarafından onaylanması
- Hedef cihazın kullanıcı tarafından seçilmesi
- Cihaz eşleştirme
- Yerel ağ üzerinden iletişim
- Kontrollü transfer oturumları
- Bilinmeyen cihazlardan gelen isteklerin kontrol edilmesi

> **Not:** Yerel ağ kullanımı tek başına güvenlik garantisi değildir. Güvenilmeyen veya ortak kullanılan ağlarda dosya transferi gerçekleştirilirken ağ güvenliği ayrıca değerlendirilmelidir.

---

## Gereksinimler

### Windows
- Windows 10 veya üzeri
- Yerel ağ bağlantısı
- Gerekli ağ izinleri
- Gerekli dosya sistemi izinleri

### Android
- Android cihaz
- Wi-Fi bağlantısı
- Gerekli ağ izinleri
- Gerekli dosya erişim izinleri

---

## Kurulum

### Depoyu Klonlama

```bash
git clone https://github.com/k7codes/DropShare.git
cd DropShare
Android
Projeyi Android Studio ile açın.

Gradle bağımlılıklarının yüklenmesini bekledikten sonra uygulamayı fiziksel Android cihazınızda veya emülatörde çalıştırabilirsiniz.

Debug APK oluşturmak için:

bash
./gradlew assembleDebug
Windows üzerinde:

bash
.\gradlew.bat assembleDebug
APK çıktısı genellikle:

text
app/build/outputs/apk/debug/app-debug.apk
konumunda oluşturulur.

Windows
Bağımlılıkları yükleyin:

bash
dotnet restore
Projeyi derleyin:

bash
dotnet build
Release sürümü:

bash
dotnet publish -c Release
Tek dosyalı Windows çıktısı:

bash
dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
Proje Yapısı
text
DropShare/
│
├── DropShareMobile/
│   ├── app/
│   │   └── src/
│   ├── gradle/
│   ├── build.gradle
│   └── settings.gradle
│
├── DropSharePC/
│   ├── Properties/
│   ├── Resources/
│   ├── Models/
│   ├── Services/
│   ├── Network/
│   ├── Transfer/
│   └── UI/
│
├── .gitignore
├── .gitattributes
├── LICENSE
└── README.md
Teknolojiler
Android
Kotlin

Android SDK

Gradle

Windows
C#

.NET

Windows SDK

Ağ Gereksinimleri
Cihazların birbirleriyle iletişim kurabilmesi için aynı yerel ağ üzerinde bulunmaları gerekir.

Aşağıdaki durumlar cihaz keşfini veya transfer işlemlerini etkileyebilir:

Farklı Wi-Fi ağları

AP Isolation

Client Isolation

Misafir ağları

Windows Güvenlik Duvarı

VPN yazılımları

Engellenmiş bağlantı noktaları

Ağ yönlendirme sorunları

Sanal ağ adaptörleri

En sağlıklı kullanım için Android ve Windows cihazların aynı Wi-Fi ağına bağlı olması önerilir.

Geliştirme
DropShare'ın geliştirme sürecinde aşağıdaki alanlar önceliklendirilmektedir:

text
┌────────────────────────────────────────────┐
│              CİHAZ KEŞFİ                   │
├────────────────────────────────────────────┤
│ Ağ keşfi                                   │
│ Cihaz durumu                               │
│ Bağlantı yönetimi                          │
└────────────────────────────────────────────┘

┌────────────────────────────────────────────┐
│             DOSYA TRANSFERİ                │
├────────────────────────────────────────────┤
│ Dosya akışı                                │
│ Klasör aktarımı                            │
│ Büyük dosyalar                             │
│ İlerleme takibi                            │
└────────────────────────────────────────────┘

┌────────────────────────────────────────────┐
│               EŞLEŞTİRME                   │
├────────────────────────────────────────────┤
│ QR                                         │
│ Eşleştirme kodu                            │
│ Kullanıcı onayı                            │
└────────────────────────────────────────────┘
Yol Haritası
□ Geliştirilmiş cihaz keşfi
□ Daha hızlı bağlantı kurulumu
□ Geliştirilmiş bağlantı kurtarma
□ Büyük dosya aktarım optimizasyonları
□ Gelişmiş transfer kuyruğu
□ Gelişmiş transfer istatistikleri
□ Çoklu cihaz aktarımı
□ Windows Explorer entegrasyonu
□ Android Share Sheet entegrasyonu
□ Geliştirilmiş QR eşleştirme
□ Geliştirilmiş Web Drop
□ Gelişmiş bildirim kontrolleri
Katkıda Bulunma
DropShare açık kaynaklı geliştirmeye açıktır.

Depoyu fork edin:

bash
git clone https://github.com/k7codes/DropShare.git
cd DropShare
Yeni bir geliştirme dalı oluşturun:

bash
git checkout -b feature/yeni-ozellik
Değişikliklerinizi gerçekleştirin:

bash
git add .
git commit -m "Yeni özellik eklendi"
git push origin feature/yeni-ozellik
Ardından GitHub üzerinden Pull Request oluşturabilirsiniz.

Pull Request gönderirken:

Değişikliğin amacını açıkça belirtin.

Mevcut mimariye uyum sağlayın.

Gereksiz bağımlılıklar eklemeyin.

Değişiklikleri mümkün olduğunca gerçek cihazlarda test edin.

Mevcut dosya transfer işlevlerini bozmadığınızdan emin olun.

Sorun Bildirme
Bir hata ile karşılaştığınızda GitHub Issues üzerinden bildirim oluşturabilirsiniz.

Mümkün olduğunca aşağıdaki bilgileri sağlayın:

text
İşletim Sistemi:
Android Sürümü:
DropShare Sürümü:

Sorunun Açıklaması:

Tekrarlama Adımları:

Beklenen Davranış:

Gerçekleşen Davranış:

Hata Mesajı:
Ekran görüntüleri ve hata günlükleri sorunun incelenmesini kolaylaştırır.

Lisans
Bu proje, repository içerisindeki LICENSE dosyasında belirtilen lisans koşulları kapsamında dağıtılmaktadır.

<p align="center"> <img src="https://i.imgur.com/INXlgpI.png" alt="DropShare" width="64"> </p><h3 align="center">DropShare</h3><p align="center"> <strong>Fast. Local. Private.</strong> </p><p align="center"> <a href="https://github.com/k7codes/DropShare">GitHub</a> &nbsp;•&nbsp; <a href="https://github.com/k7codes/DropShare/releases">Releases</a> &nbsp;•&nbsp; <a href="https://github.com/k7codes/DropShare/issues">Issues</a> </p><p align="center"> <sub>Developed by K7~</sub> </p> ```
