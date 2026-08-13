package com.example.ag

import android.content.Context
import android.net.wifi.WifiManager
import com.example.model.BaglantiDurumu
import com.example.model.CihazModeli
import com.example.model.CihazTuru
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class KesifServisi(private val context: Context) {

    private val UDP_PORT = 52525
    private val scope = CoroutineScope(Dispatchers.IO)
    private var kesifJob: Job? = null
    private var dinlemeJob: Job? = null
    private var multicastLock: WifiManager.MulticastLock? = null

    private val _bulunanCihazlar = MutableStateFlow<Map<String, CihazModeli>>(emptyMap())
    val bulunanCihazlar: StateFlow<Map<String, CihazModeli>> = _bulunanCihazlar.asStateFlow()

    private val _taraniyor = MutableStateFlow(false)
    val taraniyor: StateFlow<Boolean> = _taraniyor.asStateFlow()

    fun kesifiBaslat(kendiCihazId: String, kendiCihazAdi: String) {
        if (kesifJob?.isActive == true) return

        _taraniyor.value = true

        try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
            multicastLock = wifiManager?.createMulticastLock("DropShareMulticastLock")?.apply {
                setReferenceCounted(true)
                acquire()
            }
        } catch (_: Exception) {}

        // Dinleme Thread'i
        dinlemeJob = scope.launch {
            var socket: DatagramSocket? = null
            try {
                socket = DatagramSocket(UDP_PORT)
                socket.broadcast = true
                val buffer = ByteArray(2048)

                while (isActive) {
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)

                    val gonderenIp = packet.address.hostAddress ?: continue
                    val mesaj = String(packet.data, 0, packet.length)

                    if (mesaj.contains("DropShare")) {
                        parseKesifMesaji(mesaj, gonderenIp, kendiCihazId)
                    }
                }
            } catch (_: Exception) {
            } finally {
                socket?.close()
            }
        }

        // Yayın Thread'i (Broadcast)
        kesifJob = scope.launch {
            val kendiIp = AagYardimcisi.yerelIpAdresiniGetir(context)

            while (isActive) {
                try {
                    val pingJson = JSONObject().apply {
                        put("protokol", "DropShare")
                        put("tip", "PING")
                        put("cihazId", kendiCihazId)
                        put("cihazAdi", kendiCihazAdi)
                        put("cihazTuru", "TELEFON")
                        put("ipAdresi", kendiIp)
                        put("port", 52526)
                        put("zaman", System.currentTimeMillis())
                    }

                    val data = pingJson.toString().toByteArray()
                    val socket = DatagramSocket()
                    socket.broadcast = true

                    val broadcastAddress = InetAddress.getByName("255.255.255.255")
                    val packet = DatagramPacket(data, data.size, broadcastAddress, UDP_PORT)
                    socket.send(packet)
                    socket.close()
                } catch (_: Exception) {}

                // Zaman aşımına uğrayan cihazları temizle (15 saniyeden eski)
                temizleEskiCihazlar()

                delay(3000)
            }
        }
    }

    private fun parseKesifMesaji(mesaj: String, gonderenIp: String, kendiCihazId: String) {
        try {
            val json = JSONObject(mesaj)
            val cihazId = json.optString("cihazId", "")
            if (cihazId.isEmpty() || cihazId == kendiCihazId) return

            val cihazAdi = json.optString("cihazAdi", "Bilinmeyen Cihaz")
            val turStr = json.optString("cihazTuru", "TELEFON")
            val port = json.optInt("port", 52526)

            val cihazTuru = when (turStr.uppercase()) {
                "BILGISAYAR", "DESKTOP", "PC", "WINDOWS" -> CihazTuru.BILGISAYAR
                "TABLET" -> CihazTuru.TABLET
                "WEB" -> CihazTuru.WEB
                else -> CihazTuru.TELEFON
            }

            val yeniCihaz = CihazModeli(
                cihazId = cihazId,
                cihazAdi = cihazAdi,
                cihazTuru = cihazTuru,
                ipAdresi = gonderenIp,
                port = port,
                baglantiDurumu = BaglantiDurumu.HAZIR,
                sonGorulmeMs = System.currentTimeMillis()
            )

            val mevcutHarita = _bulunanCihazlar.value.toMutableMap()
            mevcutHarita[cihazId] = yeniCihaz
            _bulunanCihazlar.value = mevcutHarita
        } catch (_: Exception) {}
    }

    fun elleCihazEkle(cihaz: CihazModeli) {
        val mevcutHarita = _bulunanCihazlar.value.toMutableMap()
        mevcutHarita[cihaz.cihazId] = cihaz
        _bulunanCihazlar.value = mevcutHarita
    }

    private fun temizleEskiCihazlar() {
        val simdi = System.currentTimeMillis()
        val guncel = _bulunanCihazlar.value.filter { (_, cihaz) ->
            (simdi - cihaz.sonGorulmeMs) < 20000
        }
        _bulunanCihazlar.value = guncel
    }

    fun kesifiDurdur() {
        kesifJob?.cancel()
        dinlemeJob?.cancel()
        _taraniyor.value = false
        try {
            if (multicastLock?.isHeld == true) {
                multicastLock?.release()
            }
        } catch (_: Exception) {}
    }
}
