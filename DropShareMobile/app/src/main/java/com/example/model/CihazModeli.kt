package com.example.model

enum class CihazTuru {
    TELEFON,
    BILGISAYAR,
    TABLET,
    WEB
}

enum class BaglantiDurumu {
    HAZIR,
    BAGLI,
    AKTARIYOR,
    MEZGUL,
    BAGLANTI_KESILDI
}

data class CihazModeli(
    val cihazId: String,
    val cihazAdi: String,
    val cihazTuru: CihazTuru,
    val ipAdresi: String,
    val port: Int = 52526,
    val baglantiDurumu: BaglantiDurumu = BaglantiDurumu.HAZIR,
    val sonGorulmeMs: Long = System.currentTimeMillis(),
    val eslesmis: Boolean = false,
    val desteklenenOzellikler: List<String> = listOf("DOSYA", "KLASOR", "METIN", "BAGLANTI", "WEB_DROP")
)
