package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ag.AagYardimcisi
import com.example.model.AktarimBilgisi
import com.example.model.CihazModeli
import com.example.model.CihazTuru
import com.example.model.DosyaModeli
import com.example.model.GelenDosyaIstegi
import com.example.veritabani.DropShareDepo
import com.example.veritabani.TransferGecmisiEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DropShareViewModel(application: Application) : AndroidViewModel(application) {

    val depo = DropShareDepo(application)

    val cihazAdi = depo.cihazAdi
    val otomatikKabul = depo.otomatikKabul
    val eslesmeKodu = depo.eslesmeKodu
    val bulunanCihazlarMap = depo.bulunanCihazlar
    val gelenIstek: StateFlow<GelenDosyaIstegi?> = depo.gelenIstek
    val gelenAktarim: StateFlow<AktarimBilgisi?> = depo.gelenAktarim
    val gonderilenAktarim: StateFlow<AktarimBilgisi?> = depo.gonderilenAktarim
    val webDropAktif: StateFlow<Boolean> = depo.webDropAktif

    val yerelIpAdresi = MutableStateFlow(AagYardimcisi.yerelIpAdresiniGetir(application))

    val tumGecmis = depo.tumGecmis.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _secilenCihaz = MutableStateFlow<CihazModeli?>(null)
    val secilenCihaz: StateFlow<CihazModeli?> = _secilenCihaz.asStateFlow()

    private val _secilenDosyalar = MutableStateFlow<List<DosyaModeli>>(emptyList())
    val secilenDosyalar: StateFlow<List<DosyaModeli>> = _secilenDosyalar.asStateFlow()

    private val _bildirimMesaji = MutableSharedFlow<String>()
    val bildirimMesaji: SharedFlow<String> = _bildirimMesaji.asSharedFlow()

    private val _metinPaylasimText = MutableStateFlow("")
    val metinPaylasimText: StateFlow<String> = _metinPaylasimText.asStateFlow()

    init {
        // Gelen metin paylaşımını dinle
        viewModelScope.launch {
            depo.aktarimSunucusu.gelenMetinEvent.collect { (gonderen, metin) ->
                _metinPaylasimText.value = metin
                _bildirimMesaji.emit("$gonderen bir metin/bağlantı gönderdi.")
            }
        }
    }

    fun cihazSec(cihaz: CihazModeli?) {
        _secilenCihaz.value = cihaz
    }

    fun dosyaEkleUriListesi(uriList: List<Uri>) {
        val yeniDosyalar = mutableListOf<DosyaModeli>()
        val contentResolver = getApplication<Application>().contentResolver

        for (uri in uriList) {
            var name = "dosya.bin"
            var size = 0L

            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (cursor.moveToFirst()) {
                    if (nameIndex != -1) name = cursor.getString(nameIndex) ?: "dosya.bin"
                    if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
                }
            }

            val mime = contentResolver.getType(uri) ?: "*/*"
            yeniDosyalar.add(
                DosyaModeli(
                    uri = uri,
                    dosyaAdi = name,
                    dosyaBoyutu = size,
                    mimeType = mime
                )
            )
        }

        _secilenDosyalar.value = _secilenDosyalar.value + yeniDosyalar
    }

    fun dosyaSeciminiTemizle() {
        _secilenDosyalar.value = emptyList()
    }

    fun dosyaGonder() {
        val cihaz = _secilenCihaz.value
        val dosyalar = _secilenDosyalar.value

        if (cihaz == null) {
            viewModelScope.launch { _bildirimMesaji.emit("Lütfen bir hedef cihaz seçin.") }
            return
        }
        if (dosyalar.isEmpty()) {
            viewModelScope.launch { _bildirimMesaji.emit("Gönderilecek dosya seçilmedi.") }
            return
        }

        viewModelScope.launch {
            val basarili = depo.dosyaGonder(cihaz, dosyalar)
            if (basarili) {
                _bildirimMesaji.emit("Dosyalar başarıyla gönderildi!")
                _secilenDosyalar.value = emptyList()
            } else {
                _bildirimMesaji.emit("Dosya aktarımı başarısız oldu veya reddedildi.")
            }
        }
    }

    fun metinGonder(metin: String) {
        val cihaz = _secilenCihaz.value
        if (cihaz == null) {
            viewModelScope.launch { _bildirimMesaji.emit("Lütfen hedef cihaz seçin.") }
            return
        }
        if (metin.isBlank()) return

        viewModelScope.launch {
            val basarili = depo.metinGonder(cihaz, metin)
            if (basarili) {
                _bildirimMesaji.emit("Metin/Bağlantı gönderildi!")
            } else {
                _bildirimMesaji.emit("Metin gönderilemedi.")
            }
        }
    }

    fun gelenIstegiYanitla(istekId: String, kabul: Boolean) {
        depo.gelenIstegiYanitla(istekId, kabul)
    }

    fun ipYenile() {
        yerelIpAdresi.value = AagYardimcisi.yerelIpAdresiniGetir(getApplication())
    }

    fun elleIpEkle(ip: String, ad: String) {
        if (ip.isBlank()) return
        val yeniCihaz = CihazModeli(
            cihazId = "elle_$ip",
            cihazAdi = if (ad.isBlank()) "Manuel IP ($ip)" else ad,
            cihazTuru = CihazTuru.BILGISAYAR,
            ipAdresi = ip
        )
        depo.kesifServisi.elleCihazEkle(yeniCihaz)
        _secilenCihaz.value = yeniCihaz
    }

    fun cihazAdiGuncelle(yeniAd: String) = depo.cihazAdiGuncelle(yeniAd)
    fun otomatikKabulGuncelle(aktif: Boolean) = depo.otomatikKabulGuncelle(aktif)
    fun eslesmeKoduYenile() = depo.eslesmeKoduYenile()
    fun webDropToggle() = depo.webDropToggle()
    fun gecmisiTemizle() {
        viewModelScope.launch { depo.gecmisiTemizle() }
    }

    fun aktarimiIptalEt() {
        depo.aktarimIstemcisi.aktarimiIptalEt()
    }

    fun aktarimiSifirla() {
        depo.aktarimSunucusu.aktarimiSifirla()
        depo.aktarimIstemcisi.gonderimiSifirla()
    }

    override fun onCleared() {
        super.onCleared()
        depo.servisleriKapat()
    }
}
