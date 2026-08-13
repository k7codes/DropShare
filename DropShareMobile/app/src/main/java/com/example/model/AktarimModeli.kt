package com.example.model

enum class AktarimDurumu {
    BEKLIYOR,
    ONAY_BEKLIYOR,
    AKTARILIYOR,
    DURAKLATILDI,
    TAMAMLANDI,
    REDDEDILDI,
    HATA
}

data class AktarimBilgisi(
    val aktarimId: String = System.currentTimeMillis().toString(),
    val isGelen: Boolean = false,
    val hedefCihazAdi: String,
    val hedefIp: String,
    val toplamDosyaSayisi: Int = 1,
    val mevcutDosyaIndeksi: Int = 1,
    val mevcutDosyaAdi: String,
    val aktarilanBayt: Long = 0L,
    val toplamBayt: Long = 0L,
    val aktarimHiziBaytSn: Long = 0L,
    val kalanSureSn: Long = 0L,
    val durum: AktarimDurumu = AktarimDurumu.BEKLIYOR,
    val hataMesaji: String? = null,
    val sha256Hash: String = ""
)

data class GelenDosyaIstegi(
    val istekId: String,
    val gonderenCihazAdi: String,
    val gonderenIp: String,
    val gonderenPort: Int = 52526,
    val dosyaAdi: String,
    val dosyaBoyutu: Long,
    val dosyaSayisi: Int = 1,
    val dosyaTuru: String = "Dosya",
    val sha256Hash: String = "",
    val metinIcerigi: String? = null
)
