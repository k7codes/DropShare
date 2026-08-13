<p align="center">
  <img src="https://i.imgur.com/INXlgpI.png" alt="DropShare Logo" width="128">
</p>

<h1 align="center">DropShare</h1>

<p align="center">
  <strong>Fast. Local. Private.</strong>
</p>

<p align="center">
  Android ve Windows cihazlar arasında hızlı, kolay ve yerel dosya paylaşımı.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Android-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/Windows-C%23%20%2F%20.NET-512BD4?style=for-the-badge&logo=dotnet&logoColor=white">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=android&logoColor=white">
  <img src="https://img.shields.io/badge/Network-Local%20LAN-00A67E?style=for-the-badge">
</p>

<p align="center">
  <a href="https://github.com/k7codes/DropShare">GitHub</a>
  •
  <a href="https://github.com/k7codes/DropShare/issues">Issues</a>
</p>

---

## Hakkında

**DropShare**, aynı yerel ağ üzerinde bulunan Android ve Windows cihazlar arasında hızlı ve pratik dosya aktarımı yapmak için geliştirilen çok platformlu bir dosya paylaşım uygulamasıdır.

Temel amaç, telefon ile bilgisayar arasında dosya göndermeyi mümkün olduğunca basit hale getirmektir.

DropShare'ın temel yaklaşımı:

```text
Cihaz A
   │
   │  Yerel Ağ
   ▼
Cihaz B
```

Dosyaların zorunlu bir bulut depolama servisine yüklenmesi yerine, mümkün olduğunca doğrudan yerel ağ üzerinden aktarılması hedeflenir.

**Geliştirici:** K7~

---

## Platformlar

| Platform              | Teknoloji         |
| --------------------- | ----------------- |
| Android               | Kotlin            |
| Android UI            | Jetpack Compose   |
| Android UI Components | Material 3        |
| Windows               | C# / .NET         |
| Network               | Yerel Ağ          |
| Dosya Sistemi         | Platform API'leri |

### Desteklenen aktarım senaryoları

* Android → Android
* Android → Windows
* Windows → Android
* Windows → Windows

---

## Özellikler

### Dosya Transferi

DropShare farklı dosya türlerinin cihazlar arasında aktarılmasını desteklemek üzere tasarlanmıştır.

* Tek dosya gönderme
* Birden fazla dosya gönderme
* Fotoğraf aktarımı
* Video aktarımı
* Ses dosyası aktarımı
* Belge aktarımı
* Arşiv dosyaları
* Büyük boyutlu dosya aktarımı
* Klasör aktarımı

---

### Cihaz Keşfi

Aynı yerel ağ üzerinde bulunan DropShare cihazları keşfedilebilir.

Örneğin:

```text
Yakındaki Cihazlar

DESKTOP-K7
● Hazır

LAPTOP
● Hazır

Android Telefon
● Hazır
```

Kullanıcı hedef cihazı seçerek transfer işlemini başlatabilir.

---

### Cihaz Eşleştirme

Bilinmeyen cihazlardan gelen transferlerin kullanıcı onayı olmadan kabul edilmemesi hedeflenir.

Desteklenen eşleştirme yöntemleri:

* QR kod
* Eşleştirme kodu
* Yerel ağ cihaz keşfi
* Kullanıcı onayı

Örnek:

```text
┌─────────────────────────────┐
│       Cihaz Eşleştirme      │
│                             │
│         DESKTOP-K7           │
│                             │
│     Eşleştirme Kodu          │
│          482731              │
│                             │
│        [ QR KOD ]            │
│                             │
│        [ Eşleştir ]          │
└─────────────────────────────┘
```

---

### Gelen Dosyalar

Bir cihaz dosya göndermek istediğinde hedef cihazda transfer isteği gösterilebilir.

```text
Gelen Dosya

Gönderen:
Kayra'nın Telefonu

Dosya:
video.mp4

Boyut:
1.42 GB

[ Kabul Et ]   [ Reddet ]
```

Kullanıcı kabul etmeden aktarımın başlamaması hedeflenir.

---

### Transfer Durumu

Aktarım sırasında gerçek zamanlı bilgiler görüntülenebilir.

```text
video.mp4

1.24 GB / 2.80 GB

████████████░░░░░░░░  44%

Hız:
42.8 MB/s

Tahmini kalan süre:
37 saniye
```

Gösterilebilen bilgiler:

* Dosya adı
* Aktarılan veri
* Toplam veri
* İlerleme yüzdesi
* Aktarım hızı
* Tahmini kalan süre
* Transfer durumu

---

### Transfer Kontrolü

Aktarım sırasında işlem kontrolü sağlanabilir.

* Başlat
* Duraklat
* Devam ettir
* İptal et

Bağlantı kesildiğinde kullanıcıya anlaşılır bir durum mesajı gösterilmesi hedeflenir.

---

### Pano Paylaşımı

Eşleştirilmiş cihazlar arasında metin paylaşımı yapılabilir.

Örneğin Android cihazdan:

```text
https://github.com/k7codes/DropShare
```

gönderildiğinde Windows tarafında alınan içerik görüntülenebilir.

```text
Metin Alındı

https://github.com/k7codes/DropShare

[ Kopyala ]
```

---

### Bağlantı Paylaşımı

Android paylaşım menüsü kullanılarak bağlantılar başka bir cihaza gönderilebilir.

```text
Bağlantı Gönderiliyor

https://example.com

Hedef:
DESKTOP-K7

[ Gönder ]
```

Alıcı cihazda:

```text
Bağlantı Alındı

https://example.com

[ Aç ]   [ Kopyala ]
```

---

### QR Kod ile Eşleştirme

Windows istemcisi hızlı bağlantı amacıyla QR kod oluşturabilir.

Android cihaz QR kodu tarayarak bilgisayarla eşleştirilebilir.

QR kod içerisinde yalnızca gerekli geçici bağlantı/eşleştirme bilgilerinin bulunması hedeflenir.

---

### Sürükle ve Bırak

Windows istemcisinde dosyalar doğrudan uygulama penceresine sürüklenip bırakılabilir.

```text
┌─────────────────────────────────────────┐
│                                         │
│          Dosyaları buraya bırakın       │
│                                         │
│                 veya seçin              │
│                                         │
└─────────────────────────────────────────┘
```

Dosyalar bırakıldıktan sonra hedef cihaz seçilir.

---

### Klasör Transferi

Tek dosyanın yanında klasörlerin de aktarılması hedeflenir.

Örneğin:

```text
Proje/
├── README.md
├── src/
├── assets/
├── config/
└── build.gradle
```

Klasör içerisindeki yapı korunarak tek bir transfer işlemi gerçekleştirilebilir.

---

### Transfer Geçmişi

Geçmiş transfer işlemleri görüntülenebilir.

```text
Transfer Geçmişi

Bugün

video.mp4
1.42 GB
Android → Windows
✓ Başarılı

proje.zip
384 MB
Windows → Android
✓ Başarılı

Dün

fotoğraf.jpg
8.4 MB
Android → Windows
✓ Başarılı
```

Transfer geçmişinde:

* Dosya adı
* Dosya boyutu
* Tarih
* Saat
* Gönderen
* Alıcı
* Transfer yönü
* İşlem durumu

gibi bilgiler bulunabilir.

---

## Android

Android istemcisi modern Android cihazlarda çalışacak şekilde tasarlanmıştır.

Android tarafında kullanılan temel teknolojiler:

* Kotlin
* Jetpack Compose
* Material 3
* Android Network API'leri
* Yerel ağ iletişimi
* Storage Access Framework

Android kullanıcıları:

* Yakındaki cihazları görebilir
* Cihazları eşleştirebilir
* Dosya seçebilir
* Birden fazla dosya seçebilir
* Dosya gönderebilir
* Gelen dosyaları kabul edebilir
* Gelen dosyaları reddedebilir
* Transfer ilerlemesini görüntüleyebilir
* Transfer geçmişini görüntüleyebilir
* Cihaz adını değiştirebilir
* İndirme klasörünü belirleyebilir
* Tema ayarlarını değiştirebilir

---

## Windows

Windows istemcisi Android cihazlarla bilgisayar arasında dosya aktarımı gerçekleştirmek için kullanılır.

Windows tarafında kullanılan temel teknolojiler:

* C#
* .NET
* Windows masaüstü arayüzü
* Yerel ağ iletişimi
* Dosya sistemi API'leri

Windows tarafında:

* Dosya gönderme
* Dosya alma
* Çoklu dosya aktarımı
* Klasör aktarımı
* Sürükle ve bırak
* Yakındaki cihazları görüntüleme
* QR kod ile eşleştirme
* Transfer ilerlemesi
* Transfer geçmişi

gibi özellikler bulunur.

---

## Yerel Ağ Mimarisi

DropShare'ın temel çalışma modeli yerel ağ iletişimine dayanır.

Cihazların aynı Wi-Fi veya LAN üzerinde olması durumunda istemcilerin birbirlerini keşfetmesi ve bağlantı kurması hedeflenir.

```text
                    Yerel Ağ
                       │
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
       Android      Windows       Laptop
          │            │            │
          └────────────┼────────────┘
                       │
                 Dosya Transferi
```

Bu yaklaşım sayesinde aynı ağdaki cihazlar arasında doğrudan ve hızlı bir aktarım deneyimi sağlanabilir.

---

## Güvenlik

DropShare yerel ağ kullanımını temel alırken güvenlik ve kullanıcı kontrolünü de ön planda tutmayı amaçlar.

Temel güvenlik yaklaşımı:

* Bilinmeyen cihazlardan gelen transferleri otomatik kabul etmeme
* Kullanıcı onayı
* Cihaz eşleştirme
* Transfer oturumlarının kontrolü
* Dosya bütünlüğü doğrulaması
* Geçici bağlantı bilgilerinin kullanılması

> Dosya aktarımının güvenliği kullanılan yerel ağın güvenliğine de bağlıdır. Güvenilmeyen ortak Wi-Fi ağlarında dikkatli olunması önerilir.

---

## Bulut Depolama

DropShare'ın temel amacı dosyaları bir bulut servisine yükleyip başka cihazdan indirmek değildir.

Öncelikli yaklaşım:

```text
┌──────────────┐
│   Cihaz A    │
└──────┬───────┘
       │
       │ Yerel Ağ
       │
       ▼
┌──────────────┐
│   Cihaz B    │
└──────────────┘
```

Bu yapı sayesinde aynı ağdaki cihazlar arasında daha doğrudan bir dosya aktarım deneyimi hedeflenir.

---

## Web Drop

Windows tarafında isteğe bağlı yerel web arayüzü kullanılabilir.

Örnek:

```text
http://192.168.1.100:8080
```

Aynı ağdaki başka bir cihaz bu adrese erişerek DropShare ile iletişim kurabilir.

Web arayüzünde bulunabilecek özellikler:

* Dosya gönderme
* Dosya yükleme
* Transfer durumu
* Cihaz bilgileri
* Bağlantı durumu

Web Drop genel internet kullanımı yerine yerel ağ içerisindeki kullanım amacıyla tasarlanır.

---

## Ayarlar

DropShare içerisinde aşağıdaki ayarlar bulunabilir:

```text
Ayarlar

Genel
├── Cihaz Adı
├── İndirme Klasörü
└── Bildirimler

Görünüm
├── Açık Tema
├── Koyu Tema
└── Sistem Teması

Bağlantı
├── Eşleştirilmiş Cihazlar
└── Ağ Bilgileri

Depolama
└── Transfer Geçmişini Temizle
```

---

## Tasarım

DropShare modern, sade ve profesyonel bir kullanıcı deneyimi hedefler.

Tasarım yaklaşımı:

* Modern kart yapısı
* Koyu tema
* Açık tema
* Yuvarlatılmış köşeler
* Net tipografi
* Anlaşılır ikonlar
* Gereksiz görsel kalabalıktan kaçınma
* Akıcı geçişler
* Mobil kullanım için optimize edilmiş kontroller

Ana ekranın temel amacı kullanıcıya ilk bakışta:

```text
Yakındaki Cihazlar

Dosya Gönder

Dosya Al
```

mantığını anlatmaktır.

---

## Proje Yapısı

### Android

```text
DropShareMobile/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       ├── res/
│   │       └── AndroidManifest.xml
│   │
│   ├── build.gradle
│   └── proguard-rules.pro
│
└── ...
```

### Windows

```text
DropSharePC/
├── Program.cs
├── Servisler/
├── Modeller/
├── Ağ/
├── Aktarim/
├── Arayuz/
└── Kaynaklar/
```

---

## Test Senaryoları

### Android → Windows

```text
Android
   ↓
Dosya Seç
   ↓
Windows PC
   ↓
Kabul Et
   ↓
Transfer
   ↓
Tamamlandı
```

### Windows → Android

```text
Windows
   ↓
Dosya Seç
   ↓
Android
   ↓
Kabul Et
   ↓
Transfer
   ↓
Tamamlandı
```

### Android → Android

```text
Android
   ↓
Cihaz Keşfi
   ↓
Eşleştirme
   ↓
Dosya Seç
   ↓
Transfer
```

### Windows → Windows

```text
Windows
   ↓
Cihaz Keşfi
   ↓
Hedef Cihaz
   ↓
Transfer
```

---

## Kurulum

### Android

Android projesini Android Studio ile açın.

Gerekli bağımlılıkların indirilmesinin ardından projeyi Android cihaz veya emülatör üzerinde çalıştırabilirsiniz.

```bash
./gradlew assembleDebug
```

Windows üzerinde:

```powershell
.\gradlew.bat assembleDebug
```

APK oluşturulduktan sonra `app/build/outputs/apk/` altında bulunabilir.

### Windows

Windows projesini Visual Studio ile açın.

Gerekli .NET bağımlılıklarını yükledikten sonra projeyi derleyip çalıştırabilirsiniz.

```bash
dotnet restore
dotnet build
```

---

## Kullanım

1. Android ve Windows cihazlara DropShare'ı kurun.
2. Cihazları aynı Wi-Fi veya yerel ağa bağlayın.
3. DropShare'ı her iki cihazda da açın.
4. Yakındaki cihazların keşfedilmesini bekleyin.
5. Hedef cihazı seçin.
6. Göndermek istediğiniz dosyaları seçin.
7. Transfer isteğini gönderin.
8. Karşı cihazdaki kullanıcı isteği kabul ettiğinde aktarım başlar.
9. Transfer ilerlemesini gerçek zamanlı olarak takip edin.

---

## Neden DropShare?

DropShare'ın amacı dosya paylaşımını mümkün olduğunca basit hale getirmektir.

```text
Hızlı
   +
Yerel
   +
Basit
   +
Kullanıcı Kontrollü
   =
DropShare
```

Bulut hesabı oluşturmak veya dosyaları önce bir sunucuya yüklemek yerine, aynı yerel ağdaki cihazlar arasında doğrudan aktarım deneyimine odaklanır.

---

## Geliştirme

DropShare açık kaynak bir geliştirme projesidir.

Katkıda bulunmak için:

```bash
git clone https://github.com/k7codes/DropShare.git
cd DropShare
```

Daha sonra ilgili platform projesini Android Studio veya Visual Studio ile açabilirsiniz.

---

## Lisans

Bu proje için lisans bilgileri repository içerisinde ayrıca belirtilmektedir.

---

## Geliştirici

<p align="center">
  <strong>Developed By K7~</strong>
</p>

<p align="center">
  <a href="https://github.com/k7codes">
    github.com/k7codes
  </a>
</p>

---

<p align="center">
  <strong>DropShare</strong><br>
  Fast. Local. Private.
</p>
