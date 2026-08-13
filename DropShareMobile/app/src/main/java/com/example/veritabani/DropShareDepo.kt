package com.example.veritabani

import android.content.Context
import com.example.ag.AagYardimcisi
import com.example.ag.AktarimIstemcisi
import com.example.ag.AktarimSunucusu
import com.example.ag.KesifServisi
import com.example.ag.WebDropSunucusu
import com.example.model.AktarimBilgisi
import com.example.model.CihazModeli
import com.example.model.DosyaModeli
import com.example.model.GelenDosyaIstegi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class DropShareDepo(private val context: Context) {

    private val veritabani = DropShareVeritabani.veritabaniGetir(context)
    private val gecmisiDao = veritabani.transferGecmisiDao()

    val kesifServisi = KesifServisi(context)
    val aktarimSunucusu = AktarimSunucusu(context)
    val aktarimIstemcisi = AktarimIstemcisi(context)
    val webDropSunucusu = WebDropSunucusu(context)

    private val prefs = context.getSharedPreferences("dropshare_ayarlar", Context.MODE_PRIVATE)

    private val _cihazAdi = MutableStateFlow(prefs.getString("cihaz_adi", AagYardimcisi.varsayilanCihazAdiniGetir()) ?: "DropShare Cihazı")
    val cihazAdi: StateFlow<String> = _cihazAdi.asStateFlow()

    private val _otomatikKabul = MutableStateFlow(prefs.getBoolean("otomatik_kabul", false))
    val otomatikKabul: StateFlow<Boolean> = _otomatikKabul.asStateFlow()

    private val _kendiCihazId = prefs.getString("cihaz_id", null) ?: UUID.randomUUID().toString().also {
        prefs.edit().putString("cihaz_id", it).apply()
    }

    private val _eslesmeKodu = MutableStateFlow(olusturAltihaneliKod())
    val eslesmeKodu: StateFlow<String> = _eslesmeKodu.asStateFlow()

    val tumGecmis: Flow<List<TransferGecmisiEntity>> = gecmisiDao.tumGecmisiGetir()
    val tumEslesmisCihazlar: Flow<List<EslesmisCihazEntity>> = gecmisiDao.tumEslesmisCihazlariGetir()

    val bulunanCihazlar: StateFlow<Map<String, CihazModeli>> = kesifServisi.bulunanCihazlar
    val gelenIstek: StateFlow<GelenDosyaIstegi?> = aktarimSunucusu.gelenIstek
    val gelenAktarim: StateFlow<AktarimBilgisi?> = aktarimSunucusu.aktifAktarim
    val gonderilenAktarim: StateFlow<AktarimBilgisi?> = aktarimIstemcisi.aktifGonderim
    val webDropAktif: StateFlow<Boolean> = webDropSunucusu.calisiyor

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        // Sunucu ve Keşif Servisini başlat
        servisleriBaslat()
    }

    fun servisleriBaslat() {
        aktarimSunucusu.sunucuyuBaslat()
        kesifServisi.kesifiBaslat(_kendiCihazId, _cihazAdi.value)
    }

    fun cihazAdiGuncelle(yeniAd: String) {
        if (yeniAd.isBlank()) return
        _cihazAdi.value = yeniAd
        prefs.edit().putString("cihaz_adi", yeniAd).apply()
        kesifServisi.kesifiDurdur()
        kesifServisi.kesifiBaslat(_kendiCihazId, yeniAd)
    }

    fun otomatikKabulGuncelle(aktif: Boolean) {
        _otomatikKabul.value = aktif
        prefs.edit().putBoolean("otomatik_kabul", aktif).apply()
    }

    fun eslesmeKoduYenile() {
        _eslesmeKodu.value = olusturAltihaneliKod()
    }

    private fun olusturAltihaneliKod(): String {
        val rastgele = (100000..999999).random()
        return rastgele.toString()
    }

    suspend fun dosyaGonder(hedefCihaz: CihazModeli, dosyaListesi: List<DosyaModeli>): Boolean {
        val basarili = aktarimIstemcisi.dosyaGonder(
            hedefIp = hedefCihaz.ipAdresi,
            hedefPort = hedefCihaz.port,
            hedefCihazAdi = hedefCihaz.cihazAdi,
            kendiCihazAdi = _cihazAdi.value,
            dosyaListesi = dosyaListesi
        )

        for (d in dosyaListesi) {
            gecmisiDao.gecmisEkle(
                TransferGecmisiEntity(
                    dosyaAdi = d.dosyaAdi,
                    dosyaBoyutu = d.dosyaBoyutu,
                    dosyaTuru = d.mimeType,
                    hedefKaynakCihaz = hedefCihaz.cihazAdi,
                    yon = "GONDERILDI",
                    durum = if (basarili) "BASARILI" else "BASARISIZ",
                    sha256Hash = d.sha256Hash
                )
            )
        }

        return basarili
    }

    suspend fun metinGonder(hedefCihaz: CihazModeli, metin: String): Boolean {
        val basarili = aktarimIstemcisi.metinGonder(
            hedefIp = hedefCihaz.ipAdresi,
            hedefPort = hedefCihaz.port,
            kendiCihazAdi = _cihazAdi.value,
            metin = metin
        )

        gecmisiDao.gecmisEkle(
            TransferGecmisiEntity(
                dosyaAdi = "Metin/Bağlantı Paylaşımı",
                dosyaBoyutu = metin.length.toLong(),
                dosyaTuru = "text/plain",
                hedefKaynakCihaz = hedefCihaz.cihazAdi,
                yon = "GONDERILDI",
                durum = if (basarili) "BASARILI" else "BASARISIZ"
            )
        )

        return basarili
    }

    fun gelenIstegiYanitla(istekId: String, kabul: Boolean) {
        aktarimSunucusu.istegiYanitla(istekId, kabul)
        val istek = gelenIstek.value
        if (istek != null) {
            scope.launch {
                gecmisiDao.gecmisEkle(
                    TransferGecmisiEntity(
                        dosyaAdi = istek.dosyaAdi,
                        dosyaBoyutu = istek.dosyaBoyutu,
                        dosyaTuru = istek.dosyaTuru,
                        hedefKaynakCihaz = istek.gonderenCihazAdi,
                        yon = "ALINDI",
                        durum = if (kabul) "BASARILI" else "REDDEDILDI",
                        sha256Hash = istek.sha256Hash
                    )
                )
            }
        }
    }

    fun webDropToggle() {
        if (webDropSunucusu.calisiyor.value) {
            webDropSunucusu.sunucuyuDurdur()
        } else {
            webDropSunucusu.sunucuyuBaslat(_cihazAdi.value)
        }
    }

    suspend fun gecmisiTemizle() {
        gecmisiDao.tumGecmisiTemizle()
    }

    suspend fun eslesmisCihazEkle(cihaz: EslesmisCihazEntity) {
        gecmisiDao.eslesmisCihazEkle(cihaz)
    }

    fun servisleriKapat() {
        kesifServisi.kesifiDurdur()
        aktarimSunucusu.sunucuyuDurdur()
        webDropSunucusu.sunucuyuDurdur()
    }
}
