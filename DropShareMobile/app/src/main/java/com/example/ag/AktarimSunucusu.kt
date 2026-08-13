package com.example.ag

import android.content.Context
import android.os.Environment
import com.example.model.AktarimBilgisi
import com.example.model.AktarimDurumu
import com.example.model.GelenDosyaIstegi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.security.MessageDigest

class AktarimSunucusu(private val context: Context) {

    private val PORT = 52526
    private val scope = CoroutineScope(Dispatchers.IO)
    private var serverJob: Job? = null
    private var serverSocket: ServerSocket? = null

    private val _gelenIstek = MutableStateFlow<GelenDosyaIstegi?>(null)
    val gelenIstek: StateFlow<GelenDosyaIstegi?> = _gelenIstek.asStateFlow()

    private val _aktifAktarim = MutableStateFlow<AktarimBilgisi?>(null)
    val aktifAktarim: StateFlow<AktarimBilgisi?> = _aktifAktarim.asStateFlow()

    private val _gelenMetinEvent = MutableSharedFlow<Pair<String, String>>() // <Gonderen, Metin>
    val gelenMetinEvent: SharedFlow<Pair<String, String>> = _gelenMetinEvent.asSharedFlow()

    private var onayBeklemeHaritasi = mutableMapOf<String, (Boolean) -> Unit>()

    fun sunucuyuBaslat() {
        if (serverJob?.isActive == true) return

        serverJob = scope.launch {
            try {
                serverSocket = ServerSocket(PORT)
                while (isActive) {
                    val clientSocket = serverSocket?.accept() ?: break
                    scope.launch {
                        handleClientConnection(clientSocket)
                    }
                }
            } catch (_: Exception) {
            } finally {
                serverSocket?.close()
            }
        }
    }

    private suspend fun handleClientConnection(socket: Socket) {
        withContext(Dispatchers.IO) {
            try {
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val writer = PrintWriter(socket.getOutputStream(), true)

                val headerLine = reader.readLine() ?: return@withContext
                val json = JSONObject(headerLine)
                val tip = json.optString("tip", "")

                when (tip) {
                    "DOSYA_ISTEGI" -> {
                        val istekId = json.getString("istekId")
                        val gonderenAdi = json.optString("gonderenCihazAdi", "Bilinmeyen Cihaz")
                        val gonderenIp = socket.inetAddress.hostAddress ?: ""
                        val dosyaAdi = json.optString("dosyaAdi", "dosya.bin")
                        val dosyaBoyutu = json.optLong("dosyaBoyutu", 0L)
                        val dosyaSayisi = json.optInt("dosyaSayisi", 1)
                        val sha256 = json.optString("sha256Hash", "")

                        val istek = GelenDosyaIstegi(
                            istekId = istekId,
                            gonderenCihazAdi = gonderenAdi,
                            gonderenIp = gonderenIp,
                            dosyaAdi = dosyaAdi,
                            dosyaBoyutu = dosyaBoyutu,
                            dosyaSayisi = dosyaSayisi,
                            sha256Hash = sha256
                        )

                        var kabulEdildi: Boolean? = null
                        onayBeklemeHaritasi[istekId] = { kabul ->
                            kabulEdildi = kabul
                        }

                        _gelenIstek.value = istek

                        // Kullanıcı yanıtını bekle (maksimum 60s)
                        var beklemeMs = 0
                        while (kabulEdildi == null && beklemeMs < 60000 && isActive) {
                            kotlinx.coroutines.delay(200)
                            beklemeMs += 200
                        }

                        val yanit = JSONObject()
                        if (kabulEdildi == true) {
                            yanit.put("durum", "KABUL")
                            writer.println(yanit.toString())

                            // Dosya verisini oku
                            gelenDosyaVerisiniAl(socket, dosyaAdi, dosyaBoyutu, gonderenAdi, gonderenIp, sha256)
                        } else {
                            yanit.put("durum", "RED")
                            writer.println(yanit.toString())
                        }
                        _gelenIstek.value = null
                    }
                    "METIN_PAYLASIMI" -> {
                        val gonderen = json.optString("gonderenCihazAdi", "Bilinmeyen Cihaz")
                        val metin = json.optString("metinIcerigi", "")
                        if (metin.isNotEmpty()) {
                            _gelenMetinEvent.emit(Pair(gonderen, metin))
                        }
                        val yanit = JSONObject().apply { put("durum", "TAMAM") }
                        writer.println(yanit.toString())
                    }
                }
            } catch (_: Exception) {
            } finally {
                socket.close()
            }
        }
    }

    private suspend fun gelenDosyaVerisiniAl(
        socket: Socket,
        dosyaAdi: String,
        toplamBoyut: Long,
        gonderenAdi: String,
        gonderenIp: String,
        beklenenHash: String
    ) {
        withContext(Dispatchers.IO) {
            val indirmeDizini = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "DropShare"
            )
            if (!indirmeDizini.exists()) {
                indirmeDizini.mkdirs()
            }

            var hedefDosya = File(indirmeDizini, dosyaAdi)
            var sayac = 1
            while (hedefDosya.exists()) {
                val isim = dosyaAdi.substringBeforeLast(".")
                val uzanti = dosyaAdi.substringAfterLast(".", "")
                val yeniIsim = if (uzanti.isNotEmpty()) "$isim ($sayac).$uzanti" else "$isim ($sayac)"
                hedefDosya = File(indirmeDizini, yeniIsim)
                sayac++
            }

            val inputStream = socket.getInputStream()
            val fileOutputStream = FileOutputStream(hedefDosya)

            val buffer = ByteArray(64 * 1024)
            var toplamOkunan = 0L
            val baslangicZamani = System.currentTimeMillis()
            var sonGuncellemeZamani = baslangicZamani
            var sonOkunanBayt = 0L
            val digest = MessageDigest.getInstance("SHA-256")

            _aktifAktarim.value = AktarimBilgisi(
                isGelen = true,
                hedefCihazAdi = gonderenAdi,
                hedefIp = gonderenIp,
                mevcutDosyaAdi = hedefDosya.name,
                toplamBayt = toplamBoyut,
                durum = AktarimDurumu.AKTARILIYOR,
                sha256Hash = beklenenHash
            )

            try {
                while (toplamOkunan < toplamBoyut) {
                    val kalan = (toplamBoyut - toplamOkunan).coerceAtMost(buffer.size.toLong()).toInt()
                    val read = inputStream.read(buffer, 0, kalan)
                    if (read == -1) break

                    fileOutputStream.write(buffer, 0, read)
                    digest.update(buffer, 0, read)
                    toplamOkunan += read

                    val simdi = System.currentTimeMillis()
                    if (simdi - sonGuncellemeZamani >= 500 || toplamOkunan == toplamBoyut) {
                        val farkZamaniSec = ((simdi - sonGuncellemeZamani) / 1000.0).coerceAtLeast(0.1)
                        val farkBayt = toplamOkunan - sonOkunanBayt
                        val anlikHiz = (farkBayt / farkZamaniSec).toLong()

                        val kalanBayt = toplamBoyut - toplamOkunan
                        val kalanSureSec = if (anlikHiz > 0) kalanBayt / anlikHiz else 0L

                        _aktifAktarim.value = _aktifAktarim.value?.copy(
                            aktarilanBayt = toplamOkunan,
                            aktarimHiziBaytSn = anlikHiz,
                            kalanSureSn = kalanSureSec
                        )

                        sonGuncellemeZamani = simdi
                        sonOkunanBayt = toplamOkunan
                    }
                }
                fileOutputStream.flush()

                val hesaplananHash = digest.digest().joinToString("") { "%02x".format(it) }
                val hashDogru = beklenenHash.isEmpty() || beklenenHash.equals(hesaplananHash, ignoreCase = true)

                if (toplamOkunan >= toplamBoyut && hashDogru) {
                    _aktifAktarim.value = _aktifAktarim.value?.copy(
                        aktarilanBayt = toplamBoyut,
                        durum = AktarimDurumu.TAMAMLANDI
                    )
                } else {
                    _aktifAktarim.value = _aktifAktarim.value?.copy(
                        durum = AktarimDurumu.HATA,
                        hataMesaji = if (!hashDogru) "Dosya bütünlüğü doğrulanamadı (SHA-256 Uyuşmazlığı)" else "Eksik dosya aktarımı"
                    )
                }
            } catch (e: Exception) {
                _aktifAktarim.value = _aktifAktarim.value?.copy(
                    durum = AktarimDurumu.HATA,
                    hataMesaji = e.localizedMessage ?: "Aktarım hatası"
                )
            } finally {
                fileOutputStream.close()
            }
        }
    }

    fun istegiYanitla(istekId: String, kabulEt: Boolean) {
        onayBeklemeHaritasi[istekId]?.invoke(kabulEt)
        onayBeklemeHaritasi.remove(istekId)
    }

    fun aktarimiSifirla() {
        _aktifAktarim.value = null
    }

    fun sunucuyuDurdur() {
        serverJob?.cancel()
        try {
            serverSocket?.close()
        } catch (_: Exception) {}
    }
}
