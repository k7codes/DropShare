using System;
using System.Text.Json.Serialization;
using DropShare.Desktop.Services;

namespace DropShare.Desktop.Models
{
    public enum CihazTuru
    {
        TELEFON,
        TABLET,
        BILGISAYAR,
        WEB
    }

    public enum AktarimDurumu
    {
        HAZIR,
        ONAY_BEKLIYOR,
        AKTARILIYOR,
        TAMAMLANDI,
        REDDEDILDI,
        HATA
    }

    public class CihazModeli
    {
        [JsonPropertyName("cihazId")]
        public string CihazId { get; set; } = Guid.NewGuid().ToString();

        [JsonPropertyName("cihazAdi")]
        public string CihazAdi { get; set; } = Environment.MachineName;

        [JsonPropertyName("cihazTuru")]
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public CihazTuru CihazTuru { get; set; } = CihazTuru.BILGISAYAR;

        [JsonPropertyName("ipAdresi")]
        public string IpAdresi { get; set; } = "";

        [JsonPropertyName("port")]
        public int Port { get; set; } = 52526;

        [JsonPropertyName("sonGorulmeMs")]
        public long SonGorulmeMs { get; set; } = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();

        public string CihazSimgesi => CihazTuru switch
        {
            CihazTuru.TELEFON => "📱",
            CihazTuru.TABLET => "📲",
            CihazTuru.WEB => "🌐",
            _ => "💻"
        };

        public string TurMetni => CihazTuru switch
        {
            CihazTuru.TELEFON => "Android Telefon",
            CihazTuru.TABLET => "Tablet",
            CihazTuru.WEB => "Web Tarayıcı",
            _ => "Windows PC"
        };
    }

    public class KesifPaketi
    {
        [JsonPropertyName("protokol")]
        public string Protokol { get; set; } = "DropShare";

        [JsonPropertyName("tip")]
        public string Tip { get; set; } = "PING";

        [JsonPropertyName("cihazId")]
        public string CihazId { get; set; } = "";

        [JsonPropertyName("cihazAdi")]
        public string CihazAdi { get; set; } = "";

        [JsonPropertyName("cihazTuru")]
        public string CihazTuru { get; set; } = "BILGISAYAR";

        [JsonPropertyName("ipAdresi")]
        public string IpAdresi { get; set; } = "";

        [JsonPropertyName("port")]
        public int Port { get; set; } = 52526;

        [JsonPropertyName("zaman")]
        public long Zaman { get; set; } = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
    }

    public class GelenDosyaIstegi
    {
        [JsonPropertyName("tip")]
        public string Tip { get; set; } = "DOSYA_ISTEGI";

        [JsonPropertyName("istekId")]
        public string IstekId { get; set; } = Guid.NewGuid().ToString();

        [JsonPropertyName("gonderenCihazAdi")]
        public string GonderenCihazAdi { get; set; } = "";

        [JsonPropertyName("dosyaAdi")]
        public string DosyaAdi { get; set; } = "";

        [JsonPropertyName("dosyaBoyutu")]
        public long DosyaBoyutu { get; set; }

        [JsonPropertyName("dosyaSayisi")]
        public int DosyaSayisi { get; set; } = 1;

        [JsonPropertyName("sha256Hash")]
        public string Sha256Hash { get; set; } = "";

        [JsonIgnore]
        public string GonderenIp { get; set; } = "";
    }

    public class MetinPaylasimPaketi
    {
        [JsonPropertyName("tip")]
        public string Tip { get; set; } = "METIN_PAYLASIMI";

        [JsonPropertyName("gonderenCihazAdi")]
        public string GonderenCihazAdi { get; set; } = "";

        [JsonPropertyName("metinIcerigi")]
        public string MetinIcerigi { get; set; } = "";
    }

    public class AktarimGecmisOgesi
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string DosyaAdi { get; set; } = "";
        public string TamYol { get; set; } = "";
        public long DosyaBoyutu { get; set; }
        public string CihazAdi { get; set; } = "";
        public bool IsGelen { get; set; }
        public DateTime Zaman { get; set; } = DateTime.Now;
        public AktarimDurumu Durum { get; set; } = AktarimDurumu.TAMAMLANDI;
        public string HataMesaji { get; set; } = "";

        public string BoyutMetni
        {
            get
            {
                if (DosyaBoyutu <= 0) return "0 B";
                string[] birimler = { "B", "KB", "MB", "GB", "TB" };
                int i = (int)Math.Floor(Math.Log(DosyaBoyutu, 1024));
                i = Math.Clamp(i, 0, birimler.Length - 1);
                double val = DosyaBoyutu / Math.Pow(1024, i);
                return $"{val:0.##} {birimler[i]}";
            }
        }
        public string DurumSimgesi => Durum switch
        {
            AktarimDurumu.TAMAMLANDI => "✅",
            AktarimDurumu.REDDEDILDI => "🚫",
            AktarimDurumu.HATA => "❌",
            _ => "⏳"
        };
        public string YonSimgesi => IsGelen ? "⬇️" : "⬆️";
    }
}
