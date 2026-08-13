# DropShare

<p align="center">
  <img src="DropShare.ico" alt="DropShare Logo" width="128">
</p>

<h1 align="center">DropShare</h1>

<p align="center">
  <strong>Fast. Local. Private.</strong>
</p>

<p align="center">
  Android ve Windows cihazlar arasında yerel ağ üzerinden hızlı dosya paylaşımı.
</p>

<p align="center">
  <strong>Developed By K7~</strong>
</p>

---

## 📖 Hakkında

**DropShare**, aynı yerel ağ üzerinde bulunan cihazlar arasında hızlı ve pratik dosya paylaşımı sağlamak amacıyla geliştirilmiş çok platformlu bir uygulamadır.

DropShare'ın temel amacı, telefon ile bilgisayar arasında dosya aktarımını mümkün olduğunca basit hale getirmektir.

Dosya paylaşımı sırasında kullanıcı hesabı oluşturulması veya zorunlu bir bulut depolama servisine dosya yüklenmesi gerekmemesi hedeflenmiştir.

Uygulama özellikle aynı Wi-Fi veya yerel ağ üzerinde bulunan cihazlar arasında doğrudan iletişim kurulmasına odaklanmaktadır.

### Desteklenen platformlar

- 📱 Android
- 💻 Windows

### Temel kullanım senaryoları

- Android → Android
- Android → Windows
- Windows → Android
- Windows → Windows

---

# ✨ Özellikler

## 📂 Dosya Transferi

DropShare ile aynı ağdaki cihazlar arasında çeşitli dosya türleri aktarılabilir.

Desteklenen aktarım senaryoları:

- Tek dosya gönderme
- Birden fazla dosya gönderme
- Fotoğraf gönderme
- Video gönderme
- Ses dosyası gönderme
- Belge gönderme
- Arşiv dosyaları gönderme
- Büyük boyutlu dosya aktarımı
- Klasör aktarımı

Kullanıcı dosyalarını seçtikten sonra yakındaki cihazlardan birini seçerek aktarımı başlatabilir.

---

## 📱 Android Desteği

Android istemcisi modern Android cihazlarda çalışacak şekilde tasarlanmıştır.

Android tarafında kullanıcı:

- Yakındaki cihazları görüntüleyebilir
- Cihazları eşleştirebilir
- Dosya seçebilir
- Çoklu dosya seçebilir
- Dosya gönderebilir
- Gelen dosyaları kabul edebilir
- Gelen dosyaları reddedebilir
- Aktarım ilerlemesini görüntüleyebilir
- Transfer geçmişini görüntüleyebilir
- Cihaz adını değiştirebilir
- İndirme klasörünü belirleyebilir
- Tema ayarlarını değiştirebilir

---

## 💻 Windows Desteği

Windows istemcisi Android cihazlarla bilgisayar arasında dosya aktarımı gerçekleştirmek için kullanılabilir.

Windows tarafında:

- Dosya gönderme
- Dosya alma
- Çoklu dosya aktarımı
- Klasör aktarımı
- Sürükle ve bırak
- Yakındaki cihazları görüntüleme
- QR kod ile eşleştirme
- Transfer ilerlemesini görüntüleme
- Transfer geçmişini görüntüleme

özellikleri bulunur.

---

# 🌐 Yerel Ağ Mimarisi

DropShare temel olarak yerel ağ iletişimi üzerine kurulmuştur.

Android ve Windows cihazlar aynı Wi-Fi veya LAN üzerinde bulunduğunda birbirlerini keşfedebilir ve gerekli bağlantıyı oluşturabilir.

Genel çalışma mantığı:

    Yerel Ağ
        │
        ├── 📱 Android
        │
        ├── 💻 Windows PC
        │
        ├── 💻 Laptop
        │
        └── 📱 Android Telefon

Cihazlar aynı ağ üzerinde bulunduğu sürece DropShare istemcileri birbirleriyle haberleşebilir.

Bu yapı sayesinde dosyaların harici bir bulut sunucusuna yüklenmesine gerek kalmadan yerel ağ üzerinden transfer gerçekleştirilebilir.

---

# 🔎 Cihaz Keşfi

DropShare aynı yerel ağ üzerinde bulunan DropShare cihazlarını otomatik olarak keşfetmeye çalışır.

Keşfedilen cihazlar kullanıcıya cihaz adı ve cihaz türüyle birlikte gösterilir.

Örnek:

    Yakındaki Cihazlar

    💻 DESKTOP-K7
    ● Hazır

    💻 LAPTOP
    ● Hazır

    📱 Telefon
    ● Hazır

Kullanıcı listeden hedef cihazı seçerek dosya gönderme işlemini başlatabilir.

---

# 🔐 Cihaz Eşleştirme

DropShare bilinmeyen cihazlardan gelen transferlerin kullanıcı onayı olmadan kabul edilmemesi prensibiyle tasarlanmıştır.

Eşleştirme yöntemleri:

- QR kod
- Eşleştirme kodu
- Yerel ağ cihaz keşfi

Kullanıcı transfer yapmadan önce doğru cihazı seçer ve aktarım açık kullanıcı onayından sonra başlar.

Örnek eşleştirme ekranı:

    Cihaz Eşleştirme

    DESKTOP-K7

    Eşleştirme Kodu:
    482731

    veya

    QR kodu tarayarak bağlan

    [ Eşleştir ]

Eşleştirme tamamlandıktan sonra iki cihaz birbirini DropShare cihazı olarak tanıyabilir.

---

# 📥 Gelen Dosyalar

Başka bir cihaz dosya göndermek istediğinde hedef cihaz üzerinde gelen dosya bildirimi gösterilir.

Örnek:

    Gelen Dosya

    Gönderen:
    Kayra'nın Telefonu

    Dosya:
    video.mp4

    Boyut:
    1.42 GB

    [ Kabul Et ]    [ Reddet ]

Birden fazla dosya geldiğinde:

    Gelen Dosyalar

    4 dosya
    Toplam boyut: 834 MB

    📄 belge.pdf
    🖼️ fotoğraf.jpg
    🎵 ses.mp3
    📦 arşiv.zip

    [ Tümünü Kabul Et ]
    [ Reddet ]

Kullanıcı dosyaları kabul etmeden aktarımın başlatılması engellenir.

---

# 📤 Dosya Gönderme

Gönderme işlemi birkaç basit adımdan oluşur:

    1. Dosyaları seç
    2. Yakındaki cihazlardan birini seç
    3. Transfer isteğini gönder
    4. Karşı cihazın onayını bekle
    5. Aktarımı başlat
    6. Transfer sonucunu göster

Örnek:

    Dosya Gönder

    Seçilen Dosyalar

    📄 belge.pdf
    🖼️ fotoğraf.jpg
    📦 proje.zip

    Toplam:
    438 MB

    Hedef:
    💻 DESKTOP-K7

    [ Gönder ]

---

# 📊 Transfer Durumu

Transfer sırasında gerçek zamanlı aktarım bilgileri görüntülenebilir.

Örnek:

    video.mp4

    1.24 GB / 2.80 GB

    ████████████░░░░░░░░

    %44

    42.8 MB/s

    Tahmini kalan süre:
    37 saniye

    [ Duraklat ]    [ İptal ]

Gösterilebilen bilgiler:

- Dosya adı
- Aktarılan veri
- Toplam veri
- Yüzde
- Aktarım hızı
- Tahmini kalan süre
- Transfer durumu

---

# ⏸️ Transfer Kontrolü

Aktarım sürecinde kullanıcı işlemi kontrol edebilir.

Desteklenen işlemler:

- Transfer başlatma
- Transfer duraklatma
- Transfer devam ettirme
- Transfer iptal etme

Aktarım sırasında bağlantı koparsa kullanıcıya anlaşılır bir hata mesajı gösterilir.

Örnek:

    Transfer Duraklatıldı

    Cihaz bağlantısı geçici olarak kesildi.

    Bağlantının yeniden kurulması bekleniyor.

    [ İptal Et ]

---

# 📋 Pano Paylaşımı

Eşleştirilmiş cihazlar arasında metin paylaşımı yapılabilir.

Örnek olarak Android cihazdan:

    https://github.com/

metni gönderildiğinde Windows tarafında alınan içerik görüntülenebilir.

Windows tarafı:

    Metin Alındı

    https://github.com/

    [ Kopyala ]

---

# 🔗 Bağlantı Paylaşımı

Android paylaşım menüsü kullanılarak bir bağlantı DropShare üzerinden başka bir cihaza gönderilebilir.

Örnek:

    Bağlantı Gönderiliyor

    https://example.com

    Hedef:
    💻 DESKTOP-K7

    [ Gönder ]

Karşı cihazda:

    Bağlantı Alındı

    https://example.com

    [ Aç ]    [ Kopyala ]

---

# 📱 QR Kod ile Eşleştirme

Windows istemcisi hızlı bağlantı için QR kod oluşturabilir.

Android kullanıcıları QR kodu kamerayla tarayarak bilgisayarla eşleştirme işlemini gerçekleştirebilir.

QR kod içerisinde yalnızca gerekli bağlantı ve geçici eşleştirme bilgilerinin bulunması hedeflenmektedir.

Örnek:

    DropShare

    Cihazı Eşleştir

    [ QR KOD ]

    veya

    Eşleştirme Kodu:
    482731

---

# 🖱️ Sürükle ve Bırak

Windows istemcisinde dosyaların doğrudan uygulama penceresine sürüklenip bırakılması desteklenebilir.

Örnek arayüz:

    ┌───────────────────────────────────────────┐
    │                                           │
    │         Dosyaları buraya bırakın          │
    │                                           │
    │               veya seçin                  │
    │                                           │
    └───────────────────────────────────────────┘

Dosyalar bırakıldıktan sonra hedef cihaz seçimi yapılır.

---

# 📁 Klasör Transferi

DropShare yalnızca tek dosyaları değil, klasörleri de aktarmak üzere tasarlanmıştır.

Örneğin:

    Proje/
    ├── README.md
    ├── src/
    ├── assets/
    ├── config/
    └── build.gradle

klasörü tek bir transfer işlemi üzerinden gönderilebilir.

---

# 🗃️ Transfer Geçmişi

DropShare geçmiş transfer işlemlerini görüntülemek için transfer kayıtları oluşturabilir.

Örnek:

    Transfer Geçmişi

    Bugün

    📤 video.mp4
    1.42 GB
    Android → Windows
    ✓ Başarılı

    📥 proje.zip
    384 MB
    Windows → Android
    ✓ Başarılı

    Dün

    📤 fotoğraf.jpg
    8.4 MB
    Android → Windows
    ✓ Başarılı

Transfer kayıtlarında:

- Dosya adı
- Dosya boyutu
- Tarih
- Saat
- Gönderen
- Alıcı
- Transfer yönü
- İşlem durumu

gibi bilgiler görüntülenebilir.

---

# 📡 Bağlantı Durumu

DropShare kullanıcıya ağ durumunu mümkün olduğunca anlaşılır şekilde göstermelidir.

Örnek:

    Ağ Durumu

    ● Bağlı

    Ağ:
    Ev Wi-Fi

    IP:
    192.168.1.25

    Yerel Ağ:
    Hazır

---

# ⚙️ Ayarlar

Ayarlar bölümünde aşağıdaki seçenekler bulunabilir:

- Cihaz adı
- İndirme klasörü
- Bildirimler
- Tema
- Eşleştirilmiş cihazlar
- Transfer geçmişini temizleme
- Ağ bilgileri
- Uygulama hakkında

Örnek:

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

---

# 🎨 Tasarım

DropShare modern, sade ve profesyonel bir kullanıcı arayüzüne sahip olacak şekilde tasarlanmıştır.

Tasarım yaklaşımı:

- Modern kart yapısı
- Koyu tema
- Açık tema
- Yuvarlatılmış köşeler
- Net tipografi
- Anlaşılır ikonlar
- Gereksiz görsel kalabalıktan kaçınma
- Akıcı geçişler
- Mobil kullanım için optimize edilmiş kontroller

Ana ekran kullanıcıya ilk bakışta:

    "Yakındaki cihazlar"
    "Dosya gönder"
    "Dosya al"

mantığını anlatmalıdır.

---

# 🧩 Teknik Yapı

Android tarafında temel olarak:

- Kotlin
- Jetpack Compose
- Material 3
- Android Network API'leri
- Yerel ağ iletişimi
- Storage Access Framework

gibi teknolojiler kullanılabilir.

Windows istemcisinde:

- C#
- .NET
- Windows masaüstü arayüzü
- Yerel ağ iletişimi
- Dosya sistemi API'leri

kullanılabilir.

---

# 🔒 Güvenlik Yaklaşımı

DropShare yerel ağ kullanımını temel almasına rağmen güvenlik konusunu göz ardı etmez.

Temel güvenlik yaklaşımı:

- Bilinmeyen cihazlardan gelen transferlerin otomatik kabul edilmemesi
- Kullanıcı onayı
- Cihaz eşleştirme
- Transfer oturumlarının kontrol edilmesi
- Dosya bütünlüğü doğrulaması
- Geçici bağlantı bilgilerinin kullanılması

Dosya aktarımının güvenliği kullanılan ağın güvenliğine de bağlıdır.

Bu nedenle güvenilmeyen ortak Wi-Fi ağlarında dikkatli olunmalıdır.

---

# 🚫 Bulut Depolama Yaklaşımı

DropShare'ın temel amacı dosyaları bir bulut servisine yükleyip başka cihazdan indirmek değildir.

Öncelikli yaklaşım:

    Cihaz A
       │
       │ Yerel Ağ
       ▼
    Cihaz B

şeklinde yerel cihazlar arası veri aktarımıdır.

Bu sayede aynı ağdaki cihazlar arasında daha doğrudan bir aktarım deneyimi sağlanabilir.

---

# 🌍 Web Drop

Windows tarafında isteğe bağlı bir yerel web arayüzü bulunabilir.

Örneğin:

    http://192.168.1.100:8080

Aynı ağdaki başka bir cihaz bu adrese erişerek DropShare ile iletişim kurabilir.

Web arayüzünde:

- Dosya gönderme
- Dosya yükleme
- Transfer durumu
- Cihaz bilgileri
- Bağlantı durumu

gibi özellikler bulunabilir.

Bu özellik genel internet kullanımı için değil, yerel ağ içerisindeki kullanım için tasarlanmıştır.

---

# 🏗️ Proje Yapısı

Örnek Android proje yapısı:

    app/
    ├── src/
    │   └── main/
    │       ├── java/
    │       ├── res/
    │       └── AndroidManifest.xml
    │
    ├── build.gradle
    └── proguard-rules.pro

Windows tarafında:

    DropShare/
    ├── Program.cs
    ├── Servisler/
    ├── Modeller/
    ├── Ağ/
    ├── Aktarim/
    ├── Arayuz/
    └── Kaynaklar/

Proje içerisinde anlaşılır ve düzenli bir yapı kullanılmaktadır.

---

# 🧪 Test Senaryoları

DropShare geliştirilirken aşağıdaki senaryoların test edilmesi önerilir:

### Senaryo 1 — Android → Windows

    Android
       ↓
    Dosya Seç
       ↓
    Windows PC
       ↓
    Kabul Et
       ↓
    Transfer

### Senaryo 2 — Windows → Android

    Windows
       ↓
    Dosya Seç
       ↓
    Android
       ↓
    Kabul Et
       ↓
    Transfer

### Senaryo 3 — Büyük Dosya

    1 GB+
       ↓
    Transfer başlat
       ↓
    Hız kontrolü
       ↓
    İlerleme kontrolü
       ↓
    Dosya bütünlüğü kontrolü

### Senaryo 4 — Bağlantı Kesilmesi

    Transfer
       ↓
    Ağ bağlantısı kesildi
       ↓
    Hata / bekleme
       ↓
    Kullanıcı bildirimi

---

# 📌 Bilinen Sınırlamalar

Yerel ağ üzerinden çalışan uygulamalarda cihazların aynı ağda bulunması önemlidir.

Aşağıdaki durumlarda cihaz keşfi veya transfer sorunları oluşabilir:

- Farklı Wi-Fi ağları
- Misafir Wi-Fi ağları
- AP isolation
- Firewall kuralları
- VPN bağlantıları
- Güvenlik duvarı engellemeleri
- Sanal ağ adaptörleri
- Emülatör ağları
- Ağ yönlendirme problemleri

Özellikle Android emülatörleri ve sanal cihazlarda gerçek fiziksel cihazlara göre farklı ağ davranışları görülebilir.

Bu nedenle ağ özelliklerinin test edilmesi gerçek Android cihaz üzerinde de yapılmalıdır.

---

# 🚀 Kurulum

## Android

1. Projeyi Android Studio ile açın.
2. Gradle bağımlılıklarının tamamlanmasını bekleyin.
3. Android cihazınızı bağlayın.
4. Uygulamayı derleyin.
5. APK'yı cihazınıza yükleyin.

Debug APK örneği:

    app/build/outputs/apk/debug/app-debug.apk

---

## Windows

Windows projesini Visual Studio veya .NET CLI üzerinden derleyebilirsiniz.

Release publish örneği:

    dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true

Oluşturulan dosya publish klasörü içerisinde bulunur.

---

# 🖥️ Windows Sistem Gereksinimleri

Önerilen:

- Windows 10
- Windows 11
- x64 işlemci
- Aynı yerel ağ bağlantısı
- Dosya yazma izinleri
- Gerekli güvenlik duvarı izinleri

---

# 📱 Android Sistem Gereksinimleri

Önerilen:

- Android 8.0 veya üzeri
- Wi-Fi bağlantısı
- Dosya erişimi için gerekli kullanıcı izinleri
- Aynı yerel ağ üzerindeki diğer DropShare istemcileri

Android sürümüne göre ağ davranışları ve izin gereksinimleri değişebilir.

---

# 🛠️ Geliştirme

Projeye katkı sağlamak isteyen geliştiriciler aşağıdaki alanlarda geliştirme yapabilir:

- Ağ keşfi optimizasyonu
- Transfer performansı
- UI iyileştirmeleri
- Daha iyi hata yönetimi
- Transfer geçmişi
- QR eşleştirme
- Web Drop
- Windows entegrasyonu
- Android paylaşım menüsü entegrasyonu
- Büyük dosya transferi optimizasyonu

---

# 🔮 Gelecek Planları

Planlanan veya geliştirilebilecek özellikler:

- Gelişmiş transfer kuyruğu
- Daha iyi bağlantı kurtarma
- Gelişmiş cihaz keşfi
- Daha hızlı eşleştirme
- Windows sağ tık menüsü entegrasyonu
- Android Share Sheet entegrasyonu
- Otomatik cihaz favorileri
- Gelişmiş transfer istatistikleri
- Daha gelişmiş QR eşleştirme
- Çoklu cihaz transferleri
- Daha gelişmiş Web Drop
- Bildirim üzerinden transfer kontrolü

---

# 🤝 Katkı

Projeye katkı sağlamak isteyen geliştiriciler pull request gönderebilir.

Önerilen katkı süreci:

1. Repository'yi fork edin.
2. Yeni bir branch oluşturun.
3. Değişikliklerinizi yapın.
4. Testlerinizi gerçekleştirin.
5. Commit oluşturun.
6. Pull request gönderin.

---

# 📄 Lisans

Bu proje için kullanılacak lisans bilgileri repository üzerindeki LICENSE dosyasında belirtilir.

Lisans koşullarına uygun olmayan şekilde dağıtım yapılmamalıdır.

---

# ⚠️ Sorumluluk Reddi

DropShare yalnızca yasal ve yetkili kullanım amacıyla geliştirilmiştir.

Uygulama ile gerçekleştirilen dosya aktarımlarından, gönderilen içeriklerden veya kullanılan ağlardan kullanıcı sorumludur.

Kullanıcıların yalnızca erişim yetkisine sahip oldukları cihazlar ve ağlar üzerinde işlem yapması gerekir.

---

# 👨‍💻 Geliştirici

<p align="center">
  <strong>Developed By K7~</strong>
</p>

<p align="center">
  DropShare
</p>

<p align="center">
  Fast • Local • Simple
</p>

# DropShare

<p align="center">
  <img src="DropShare.ico" alt="DropShare Logo" width="128">
</p>

<h1 align="center">DropShare</h1>

<p align="center">
  <strong>Fast. Local. Private.</strong>
</p>

<p align="center">
  Local network file sharing between Android and Windows devices.
</p>

<p align="center">
  <strong>Developed By K7~</strong>
</p>

---

## 📖 About

**DropShare** is a multi-platform file sharing application designed to provide fast and convenient file transfers between devices connected to the same local network.

The main goal of DropShare is to make transferring files between phones and computers as simple as possible.

The project is designed around local network communication, allowing compatible devices to discover each other and transfer files without requiring a mandatory cloud storage service or user account.

### Supported Platforms

- 📱 Android
- 💻 Windows

### Main Use Cases

- Android → Android
- Android → Windows
- Windows → Android
- Windows → Windows

---

# ✨ Features

## 📂 File Transfer

DropShare can be used to transfer different types of files between devices on the same network.

Supported scenarios include:

- Single file transfer
- Multiple file transfer
- Photo transfer
- Video transfer
- Audio transfer
- Document transfer
- Archive transfer
- Large file transfer
- Folder transfer

Users can select files, choose a nearby device, and start the transfer process.

---

## 📱 Android Support

The Android client is designed for modern Android devices.

Android users can:

- Discover nearby devices
- Pair devices
- Select files
- Select multiple files
- Send files
- Accept incoming files
- Reject incoming files
- Monitor transfer progress
- View transfer history
- Change device name
- Configure download location
- Change theme settings

---

## 💻 Windows Support

The Windows client is designed to provide a simple way to transfer files between Windows computers and Android devices.

Windows features include:

- Sending files
- Receiving files
- Multiple file transfer
- Folder transfer
- Drag and drop
- Nearby device discovery
- QR pairing
- Transfer progress
- Transfer history

---

# 🌐 Local Network Architecture

DropShare is primarily based on local network communication.

When Android and Windows devices are connected to the same Wi-Fi or LAN, they can discover each other and establish the required connection.

Basic architecture:

    Local Network
        │
        ├── 📱 Android
        │
        ├── 💻 Windows PC
        │
        ├── 💻 Laptop
        │
        └── 📱 Android Phone

As long as the devices are reachable within the same network, DropShare clients can communicate with each other.

This allows file transfers to take place over the local network instead of relying on an external cloud storage service.

---

# 🔎 Device Discovery

DropShare attempts to automatically discover other DropShare clients available on the same local network.

Discovered devices can be displayed with basic information such as device name and type.

Example:

    Nearby Devices

    💻 DESKTOP-K7
    ● Ready

    💻 LAPTOP
    ● Ready

    📱 Phone
    ● Ready

Users can select a device from the list and initiate a transfer.

---

# 🔐 Device Pairing

DropShare is designed around the principle that incoming transfers from unknown devices should not be automatically accepted.

Available pairing methods may include:

- QR code
- Pairing code
- Local network discovery

The user selects the intended device before the transfer begins, and the transfer starts only after explicit confirmation.

Example:

    Device Pairing

    DESKTOP-K7

    Pairing Code:
    482731

    or

    Scan the QR code to connect

    [ Pair ]

After pairing, the devices can recognize each other as DropShare endpoints.

---

# 📥 Incoming Files

When another device attempts to send a file, the receiving device displays an incoming transfer request.

Example:

    Incoming File

    Sender:
    Kayra's Phone

    File:
    video.mp4

    Size:
    1.42 GB

    [ Accept ]    [ Reject ]

For multiple files:

    Incoming Files

    4 files
    Total size: 834 MB

    📄 document.pdf
    🖼️ photo.jpg
    🎵 audio.mp3
    📦 archive.zip

    [ Accept All ]
    [ Reject ]

The transfer is not started until the user accepts it.

---

# 📤 Sending Files

The sending process is designed to be simple:

    1. Select files
    2. Choose a nearby device
    3. Send the transfer request
    4. Wait for the receiver's approval
    5. Start transfer
    6. Display transfer result

Example:

    Send Files

    Selected Files

    📄 document.pdf
    🖼️ photo.jpg
    📦 project.zip

    Total:
    438 MB

    Target:
    💻 DESKTOP-K7

    [ Send ]

---

# 📊 Transfer Progress

Real-time transfer information can be displayed while data is being transferred.

Example:

    video.mp4

    1.24 GB / 2.80 GB

    ████████████░░░░░░░░

    44%

    42.8 MB/s

    Estimated remaining time:
    37 seconds

    [ Pause ]    [ Cancel ]

Possible information:

- File name
- Transferred data
- Total data
- Percentage
- Transfer speed
- Estimated remaining time
- Transfer status

---

# ⏸️ Transfer Control

Users can control the transfer process where supported.

Available operations may include:

- Start transfer
- Pause transfer
- Resume transfer
- Cancel transfer

If the network connection is lost, the application should provide a clear status message.

Example:

    Transfer Paused

    The device connection was temporarily lost.

    Waiting for the connection to recover.

    [ Cancel ]

---

# 📋 Clipboard Sharing

Paired devices can exchange text.

For example, Android can send:

    https://github.com/

and the Windows client can display the received text.

Example:

    Text Received

    https://github.com/

    [ Copy ]

---

# 🔗 Link Sharing

Links can be shared through DropShare using the Android share menu.

Example:

    Sending Link

    https://example.com

    Target:
    💻 DESKTOP-K7

    [ Send ]

On the receiving device:

    Link Received

    https://example.com

    [ Open ]    [ Copy ]

---

# 📱 QR Pairing

The Windows client can generate a QR code for fast pairing.

An Android device can scan the QR code to establish a pairing session with the computer.

The QR code should contain only the necessary temporary connection or pairing information.

Example:

    DropShare

    Pair Device

    [ QR CODE ]

    or

    Pairing Code:
    482731

---

# 🖱️ Drag and Drop

The Windows client can support dragging files directly into the DropShare application window.

Example:

    ┌───────────────────────────────────────────┐
    │                                           │
    │              Drop files here              │
    │                                           │
    │             or browse files               │
    │                                           │
    └───────────────────────────────────────────┘

After dropping files, the user can select the destination device.

---

# 📁 Folder Transfer

DropShare is designed to support folder transfers in addition to individual files.

For example:

    Project/
    ├── README.md
    ├── src/
    ├── assets/
    ├── config/
    └── build.gradle

The complete directory structure can be transferred as one operation where supported.

---

# 🗃️ Transfer History

DropShare can maintain transfer records for previously completed operations.

Example:

    Transfer History

    Today

    📤 video.mp4
    1.42 GB
    Android → Windows
    ✓ Completed

    📥 project.zip
    384 MB
    Windows → Android
    ✓ Completed

    Yesterday

    📤 photo.jpg
    8.4 MB
    Android → Windows
    ✓ Completed

Transfer records may contain:

- File name
- File size
- Date
- Time
- Sender
- Receiver
- Transfer direction
- Status

---

# 📡 Connection Status

DropShare can display basic network status information in a user-friendly format.

Example:

    Network Status

    ● Connected

    Network:
    Home Wi-Fi

    IP:
    192.168.1.25

    Local Network:
    Ready

---

# ⚙️ Settings

The settings section may contain:

- Device name
- Download directory
- Notifications
- Theme
- Paired devices
- Clear transfer history
- Network information
- About

Example:

    Settings

    General
    ├── Device Name
    ├── Download Directory
    └── Notifications

    Appearance
    ├── Light Theme
    ├── Dark Theme
    └── System Theme

    Connection
    ├── Paired Devices
    └── Network Information

    Storage
    └── Clear Transfer History

---

# 🎨 Design

DropShare is designed with a modern, clean, and professional interface.

Design principles:

- Modern card layout
- Dark theme
- Light theme
- Rounded corners
- Clear typography
- Simple icons
- Minimal visual clutter
- Smooth transitions
- Mobile-friendly controls

The main screen should immediately communicate:

    "Nearby devices"
    "Send files"
    "Receive files"

---

# 🧩 Technical Stack

The Android client can be built using technologies such as:

- Kotlin
- Jetpack Compose
- Material 3
- Android networking APIs
- Local network communication
- Storage Access Framework

The Windows client can be built using:

- C#
- .NET
- Windows desktop UI
- Local network communication
- File system APIs

---

# 🔒 Security Approach

Although DropShare is intended for local network usage, security remains an important part of the design.

The general security approach includes:

- Avoid automatically accepting transfers from unknown devices
- Explicit user approval
- Device pairing
- Controlled transfer sessions
- File integrity verification
- Temporary connection information

Transfer security is also affected by the security of the local network itself.

Users should therefore be careful when using DropShare on untrusted public or shared Wi-Fi networks.

---

# 🚫 Cloud Storage Approach

DropShare is not primarily designed around uploading files to a cloud server and downloading them from another device.

The preferred model is:

    Device A
       │
       │ Local Network
       ▼
    Device B

This allows compatible devices on the same local network to communicate more directly.

---

# 🌍 Web Drop

The Windows client may provide an optional local web interface.

For example:

    http://192.168.1.100:8080

Another device connected to the same network can access the address using a web browser.

Possible Web Drop features:

- File sending
- File uploading
- Transfer status
- Device information
- Connection status

The web interface is intended for local network usage rather than public internet exposure.

---

# 🏗️ Project Structure

Example Android structure:

    app/
    ├── src/
    │   └── main/
    │       ├── java/
    │       ├── res/
    │       └── AndroidManifest.xml
    │
    ├── build.gradle
    └── proguard-rules.pro

Example Windows structure:

    DropShare/
    ├── Program.cs
    ├── Services/
    ├── Models/
    ├── Network/
    ├── Transfer/
    ├── UI/
    └── Resources/

The project is intended to maintain a clear and understandable internal structure.

---

# 🧪 Testing Scenarios

The following scenarios should be tested during development.

### Scenario 1 — Android → Windows

    Android
       ↓
    Select File
       ↓
    Windows PC
       ↓
    Accept
       ↓
    Transfer

### Scenario 2 — Windows → Android

    Windows
       ↓
    Select File
       ↓
    Android
       ↓
    Accept
       ↓
    Transfer

### Scenario 3 — Large File

    1 GB+
       ↓
    Start Transfer
       ↓
    Monitor Speed
       ↓
    Monitor Progress
       ↓
    Verify Result

### Scenario 4 — Connection Loss

    Transfer
       ↓
    Network Disconnected
       ↓
    Waiting / Error Handling
       ↓
    User Notification

---

# 📌 Known Limitations

Because DropShare relies on local network communication, devices must be able to reach each other on the same network.

Device discovery or transfer may fail in situations such as:

- Different Wi-Fi networks
- Guest Wi-Fi networks
- AP isolation
- Firewall restrictions
- VPN connections
- Blocked ports
- Virtual network adapters
- Emulator networking
- Routing problems

Emulators and virtual devices may behave differently from physical Android devices.

For this reason, network functionality should also be tested on a real Android device.

---

# 🚀 Installation

## Android

1. Open the project in Android Studio.
2. Wait for Gradle dependencies to finish.
3. Connect an Android device.
4. Build the project.
5. Install the APK.

Example debug APK:

    app/build/outputs/apk/debug/app-debug.apk

---

## Windows

The Windows client can be built using Visual Studio or the .NET CLI.

Example Release command:

    dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true

The resulting executable can be found inside the publish directory.

---

# 🖥️ Windows Requirements

Recommended:

- Windows 10
- Windows 11
- x64 processor
- Local network connection
- File system permissions
- Appropriate firewall permissions

---

# 📱 Android Requirements

Recommended:

- Android 8.0 or newer
- Wi-Fi connection
- Required storage and network permissions
- Access to the same local network as other DropShare clients

Android behavior and permission requirements may vary by OS version.

---

# 🛠️ Development

Potential contribution areas include:

- Network discovery improvements
- Transfer performance
- UI improvements
- Better error handling
- Transfer history
- QR pairing
- Web Drop
- Windows integration
- Android Share Sheet integration
- Large file transfer optimization
- Multi-device transfer

---

# 🔮 Roadmap

Potential future features:

- Advanced transfer queue
- Improved reconnect support
- Faster device discovery
- Faster pairing
- Windows Explorer context menu integration
- Android Share Sheet integration
- Favorite devices
- Advanced transfer statistics
- Improved QR pairing
- Multi-device transfers
- More advanced Web Drop
- Notification-based transfer controls

---

# 🤝 Contributing

Contributions are welcome.

Suggested workflow:

1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Test the changes.
5. Create a commit.
6. Open a pull request.

---

# 📄 License

The license used by the project is defined in the repository's LICENSE file.

Please make sure your use and redistribution comply with the selected license.

---

# ⚠️ Disclaimer

DropShare is intended for legal and authorized use only.

Users are responsible for the files they transfer, the devices they access, and the networks they use.

Only transfer files between devices and networks for which you have permission.

---

# 👨‍💻 Developer

<p align="center">
  <strong>Developed By K7~</strong>
</p>

<p align="center">
  DropShare
</p>

<p align="center">
  Fast • Local • Simple
</p>
