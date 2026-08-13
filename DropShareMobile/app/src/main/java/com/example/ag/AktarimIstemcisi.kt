package com.example.ag

import android.content.Context
import com.example.model.AktarimBilgisi
import com.example.model.AktarimDurumu
import com.example.model.DosyaModeli
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.security.MessageDigest

class AktarimIstemcisi(private val context: Context) {

    private val _aktifGonderim = MutableStateFlow<AktarimBilgisi?>(null)
    val aktifGonderim: StateFlow<AktarimBilgisi?> = _aktifGonderim.asStateFlow()

    private var iptalEdildi = false

    suspend fun dosyaGonder(
        hedefIp: String,
        hedefPort: Int = 52526,
        hedefCihazAdi: String,
        kendiCihazAdi: String,
        dosyaListesi: List<DosyaModeli>
    ): Boolean = withContext(Dispatchers.IO) {
        iptalEdildi = false
        if (dosyaListesi.isEmpty()) return@withContext false

        val toplamDosyaSayisi = dosyaListesi.size
        var basariliCount = 0

        for ((index, dosya) in dosyaListesi.withIndex()) {
            if (iptalEdildi) break

            val mevcutIndeks = index + 1
            var inputStream: InputStream? = null

            try {
                if (dosya.uri != null) {
                    inputStream = context.contentResolver.openInputStream(dosya.uri)
                }
                if (inputStream == null && dosya.dosyaYolu.isNotEmpty()) {
                    val file = java.io.File(dosya.dosyaYolu)
                    if (file.exists()) inputStream = file.inputStream()
                }

                if (inputStream == null) continue

                // SHA-256 hesapla (küçük/orta boy dosyalar için)
                val sha256 = if (dosya.dosyaBoyutu < 100 * 1024 * 1024) {
                    try {
                        val md = MessageDigest.getInstance("SHA-256")
                        val buffer = ByteArray(32 * 1024)
                        var read: Int
                        val tempStream = if (dosya.uri != null) context.contentResolver.openInputStream(dosya.uri) else null
                        if (tempStream != null) {
                            while (tempStream.read(buffer).also { read = it } != -1) {
                                md.update(buffer, 0, read)
                            }
                            tempStream.close()
                        }
                        md.digest().joinToString("") { "%02x".format(it) }
                    } catch (_: Exception) { "" }
                } else ""

                val socket = Socket()
                socket.connect(InetSocketAddress(hedefIp, hedefPort), 8000)

                val writer = PrintWriter(socket.getOutputStream(), true)
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                val istekgson = JSONObject().apply {
                    put("tip", "DOSYA_ISTEGI")
                    put("istekId", System.currentTimeMillis().toString())
                    put("gonderenCihazAdi", kendiCihazAdi)
                    put("dosyaAdi", dosya.dosyaAdi)
                    put("dosyaBoyutu", dosya.dosyaBoyutu)
                    put("dosyaSayisi", toplamDosyaSayisi)
                    put("sha256Hash", sha256)
                }

                _aktifGonderim.value = AktarimBilgisi(
                    isGelen = false,
                    hedefCihazAdi = hedefCihazAdi,
                    hedefIp = hedefIp,
                    toplamDosyaSayisi = toplamDosyaSayisi,
                    mevcutDosyaIndeksi = mevcutIndeks,
                    mevcutDosyaAdi = dosya.dosyaAdi,
                    toplamBayt = dosya.dosyaBoyutu,
                    durum = AktarimDurumu.ONAY_BEKLIYOR,
                    sha256Hash = sha256
                )

                writer.println(istekgson.toString())

                val yanitLine = reader.readLine()
                if (yanitLine == null) {
                    _aktifGonderim.value = _aktifGonderim.value?.copy(
                        durum = AktarimDurumu.HATA,
                        hataMesaji = "Karşı cihaz yanıt vermedi."
                    )
                    socket.close()
                    continue
                }

                val yanitJson = JSONObject(yanitLine)
                val durum = yanitJson.optString("durum", "RED")

                if (durum == "KABUL") {
                    _aktifGonderim.value = _aktifGonderim.value?.copy(
                        durum = AktarimDurumu.AKTARILIYOR
                    )

                    val outputStream = socket.getOutputStream()
                    val buffer = ByteArray(64 * 1024)
                    var okunanBayt = 0L
                    var read: Int
                    val baslangicZamani = System.currentTimeMillis()
                    var sonGuncelleme = baslangicZamani
                    var sonOkunanBayt = 0L

                    while (inputStream.read(buffer).also { read = it } != -1) {
                        if (iptalEdildi) {
                            _aktifGonderim.value = _aktifGonderim.value?.copy(
                                durum = AktarimDurumu.REDDEDILDI,
                                hataMesaji = "Aktarım kullanıcı tarafından iptal edildi"
                            )
                            break
                        }

                        outputStream.write(buffer, 0, read)
                        okunanBayt += read

                        val simdi = System.currentTimeMillis()
                        if (simdi - sonGuncelleme >= 500 || okunanBayt == dosya.dosyaBoyutu) {
                            val farkSec = ((simdi - sonGuncelleme) / 1000.0).coerceAtLeast(0.1)
                            val farkBayt = okunanBayt - sonOkunanBayt
                            val anlikHiz = (farkBayt / farkSec).toLong()

                            val kalanBayt = dosya.dosyaBoyutu - okunanBayt
                            val kalanSureSec = if (anlikHiz > 0) kalanBayt / anlikHiz else 0L

                            _aktifGonderim.value = _aktifGonderim.value?.copy(
                                aktarilanBayt = okunanBayt,
                                aktarimHiziBaytSn = anlikHiz,
                                kalanSureSn = kalanSureSec
                            )

                            sonGuncelleme = simdi
                            sonOkunanBayt = okunanBayt
                        }
                    }

                    outputStream.flush()
                    if (!iptalEdildi && okunanBayt >= dosya.dosyaBoyutu) {
                        basariliCount++
                        _aktifGonderim.value = _aktifGonderim.value?.copy(
                            aktarilanBayt = dosya.dosyaBoyutu,
                            durum = AktarimDurumu.TAMAMLANDI
                        )
                    }
                } else {
                    _aktifGonderim.value = _aktifGonderim.value?.copy(
                        durum = AktarimDurumu.REDDEDILDI,
                        hataMesaji = "Karşı cihaz aktarımı reddetti."
                    )
                }

                socket.close()
                inputStream.close()
            } catch (e: Exception) {
                _aktifGonderim.value = _aktifGonderim.value?.copy(
                    durum = AktarimDurumu.HATA,
                    hataMesaji = e.localizedMessage ?: "Bağlantı hatası"
                )
            } finally {
                try { inputStream?.close() } catch (_: Exception) {}
            }
        }

        return@withContext basariliCount == dosyaListesi.size
    }

    suspend fun metinGonder(
        hedefIp: String,
        hedefPort: Int = 52526,
        kendiCihazAdi: String,
        metin: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress(hedefIp, hedefPort), 5000)

            val writer = PrintWriter(socket.getOutputStream(), true)
            val json = JSONObject().apply {
                put("tip", "METIN_PAYLASIMI")
                put("gonderenCihazAdi", kendiCihazAdi)
                put("metinIcerigi", metin)
            }

            writer.println(json.toString())
            socket.close()
            true
        } catch (_: Exception) {
            false
        }
    }

    fun aktarimiIptalEt() {
        iptalEdildi = true
    }

    fun gonderimiSifirla() {
        _aktifGonderim.value = null
    }
}
