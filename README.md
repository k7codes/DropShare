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
  <a href="#geliştirme">Geliştirme</a>
</p>

---

## Genel Bakış

DropShare, aynı yerel ağ üzerindeki cihazlar arasında dosya ve veri aktarımını kolaylaştırmak için tasarlanmış modern bir dosya paylaşım uygulamasıdır.

Android ve Windows cihazları tek bir deneyim altında birleştirerek dosya, klasör, bağlantı ve pano içeriklerinin hızlı şekilde paylaşılmasını sağlar.

DropShare'ın temel yaklaşımı basittir:

> **Dosyanızı seçin. Cihazınızı seçin. Gönderin.**

Bulut hesabı oluşturmak veya dosyaları üçüncü taraf bir servise yüklemek yerine, desteklenen cihazların aynı yerel ağ üzerinden iletişim kurmasına odaklanır.

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

```text
                    ┌──────────────────────┐
                    │       DropShare      │
                    │      Yerel Ağ        │
                    └──────────┬───────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
         ┌─────────┐      ┌─────────┐      ┌─────────┐
         │ Android │      │ Windows │      │ Android │
         └────┬────┘      └────┬────┘      └────┬────┘
              │                │                │
              └────────────────┼────────────────┘
                               │
                               ▼
                       Doğrudan Transfer

Tipik bir transfer süreci:

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
QR ile Eşleştirme

DropShare, cihazların hızlı şekilde eşleştirilmesi için QR tabanlı bağlantı mekanizması kullanabilir.

┌───────────────────┐
│      Windows      │
│                   │
│   QR Oluştur      │
│                   │
│    ┌─────────┐    │
│    │ QR CODE │    │
│    └─────────┘    │
└─────────┬─────────┘
          │
          │ Tara
          ▼
┌───────────────────┐
│      Android      │
│                   │
│    QR Tara        │
│                   │
│   Eşleştirildi    │
└─────────┬─────────┘
          │
          ▼
     Dosya Transferi

QR sistemi, kullanıcıların bağlantı bilgilerini manuel olarak girmesine gerek kalmadan cihazları eşleştirmesine yardımcı olur.

Platformlar
<p align="center">
Platform	Durum
Android	Aktif
Windows	Aktif
</p>
Android

Android istemcisi mobil cihazlar için tasarlanmıştır.

Yakındaki cihazları keşfetme
Dosya seçme
Çoklu dosya seçme
Dosya gönderme
Dosya alma
Transfer onayı
Transfer geçmişi
Cihaz adı
İndirme konumu
Pano paylaşımı
Bağlantı paylaşımı
Tema ayarları
Windows

Windows istemcisi masaüstü kullanımına odaklanır.

Dosya gönderme
Dosya alma
Çoklu dosya aktarımı
Klasör aktarımı
Sürükle ve bırak
Cihaz keşfi
QR eşleştirme
Transfer ilerlemesi
Transfer geçmişi
Pano paylaşımı
Bağlantı paylaşımı
Transfer Geçmişi

Gerçekleştirilen transferler geçmiş üzerinden takip edilebilir.

┌────────────────────────────────────────────────────────────┐
│ Transfer Geçmişi                                           │
├──────────────────┬──────────┬─────────────────┬─────────────┤
│ Dosya            │ Boyut    │ Yön             │ Durum       │
├──────────────────┼──────────┼─────────────────┼─────────────┤
│ project.zip      │ 384 MB   │ Windows → Phone │ Tamamlandı  │
│ video.mp4        │ 1.42 GB  │ Phone → Windows │ Tamamlandı  │
│ documents.zip    │ 82 MB    │ Windows → Phone │ Tamamlandı  │
└──────────────────┴──────────┴─────────────────┴─────────────┘
Pano ve Bağlantı Paylaşımı

Dosya aktarımının yanında metin ve bağlantılar da cihazlar arasında paylaşılabilir.

Android
   │
   │  https://github.com/k7codes/DropShare
   ▼
Windows
   │
   ├── Aç
   └── Kopyala

Bu özellik özellikle telefon ile bilgisayar arasında kısa metinlerin ve bağlantıların hızlı şekilde aktarılması için kullanılabilir.

Güvenlik

DropShare, transfer işlemlerini kullanıcı kontrolünde tutacak şekilde tasarlanmıştır.

Temel yaklaşım:

Transfer isteklerinin kullanıcı tarafından onaylanması
Hedef cihazın kullanıcı tarafından seçilmesi
Cihaz eşleştirme
Yerel ağ üzerinden iletişim
Kontrollü transfer oturumları
Bilinmeyen cihazlardan gelen isteklerin kontrol edilmesi

Yerel ağ kullanımı tek başına güvenlik garantisi değildir. Güvenilmeyen veya ortak kullanılan ağlarda dosya transferi gerçekleştirilirken ağ güvenliği ayrıca değerlendirilmelidir.

Gereksinimler
Windows
Windows 10 veya üzeri
Yerel ağ bağlantısı
Gerekli ağ izinleri
Gerekli dosya sistemi izinleri
Android
Android cihaz
Wi-Fi bağlantısı
Gerekli ağ izinleri
Gerekli dosya erişim izinleri
Kurulum
Depoyu Klonlama
git clone https://github.com/k7codes/DropShare.git
cd DropShare
Android

Projeyi Android Studio ile açın.

Gradle bağımlılıklarının yüklenmesini bekledikten sonra uygulamayı fiziksel Android cihazınızda veya emülatörde çalıştırabilirsiniz.

Debug APK oluşturmak için:

./gradlew assembleDebug

Windows:

.\gradlew.bat assembleDebug

APK çıktısı genellikle:

app/build/outputs/apk/debug/app-debug.apk

konumunda oluşturulur.

Windows

Bağımlılıkları yükleyin:

dotnet restore

Projeyi derleyin:

dotnet build

Release sürümü:

dotnet publish -c Release

Tek dosyalı Windows çıktısı:

dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
Proje Yapısı
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
Teknoloji
Android
Kotlin
Android SDK
Gradle
Windows
C#
.NET
Windows SDK

GitHub repository'sindeki Languages bölümü, kaynak dosyalarına göre GitHub tarafından otomatik olarak hesaplanmaktadır. README içerisinde manuel ve yanıltıcı dil yüzdeleri belirtilmemiştir.

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
 Geliştirilmiş cihaz keşfi
 Daha hızlı bağlantı kurulumu
 Geliştirilmiş bağlantı kurtarma
 Büyük dosya aktarım optimizasyonları
 Gelişmiş transfer kuyruğu
 Gelişmiş transfer istatistikleri
 Çoklu cihaz aktarımı
 Windows Explorer entegrasyonu
 Android Share Sheet entegrasyonu
 Geliştirilmiş QR eşleştirme
 Geliştirilmiş Web Drop
 Gelişmiş bildirim kontrolleri
Katkıda Bulunma

DropShare açık kaynaklı geliştirmeye açıktır.

Depoyu fork edin:

git clone https://github.com/k7codes/DropShare.git
cd DropShare

Yeni bir geliştirme dalı oluşturun:

git checkout -b feature/yeni-ozellik

Değişikliklerinizi gerçekleştirin:

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

<p align="center"> <img src="https://i.imgur.com/INXlgpI.png" alt="DropShare" width="64"> </p> <h3 align="center">DropShare</h3> <p align="center"> Fast. Local. Private. </p> <p align="center"> <a href="https://github.com/k7codes/DropShare">GitHub</a> &nbsp;•&nbsp; <a href="https://github.com/k7codes/DropShare/releases">Releases</a> &nbsp;•&nbsp; <a href="https://github.com/k7codes/DropShare/issues">Issues</a> </p> <p align="center"> <sub>Developed by K7~</sub> </p> ```
