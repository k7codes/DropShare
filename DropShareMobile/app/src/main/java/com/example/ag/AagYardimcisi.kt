package com.example.ag

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import java.net.Inet4Address
import java.net.NetworkInterface
import java.security.MessageDigest
import java.util.Collections

object AagYardimcisi {

    fun yerelIpAdresiniGetir(context: Context): String {
        try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
            val wifiInfo = wifiManager?.connectionInfo
            val ipAddress = wifiInfo?.ipAddress ?: 0
            if (ipAddress != 0) {
                return String.format(
                    "%d.%d.%d.%d",
                    ipAddress and 0xff,
                    ipAddress shr 8 and 0xff,
                    ipAddress shr 16 and 0xff,
                    ipAddress shr 24 and 0xff
                )
            }
        } catch (_: Exception) {}

        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        val hostAddress = addr.hostAddress ?: ""
                        if (hostAddress.startsWith("192.168.") || hostAddress.startsWith("10.") || hostAddress.startsWith("172.")) {
                            return hostAddress
                        }
                    }
                }
            }
        } catch (_: Exception) {}

        return "127.0.0.1"
    }

    fun varsayilanCihazAdiniGetir(): String {
        val manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        val model = Build.MODEL
        return if (model.startsWith(manufacturer, ignoreCase = true)) {
            model
        } else {
            "$manufacturer $model"
        }
    }

    fun baytDönüştür(bayt: Long): String {
        if (bayt <= 0) return "0 B"
        val birimler = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(bayt.toDouble()) / Math.log10(1024.0)).toInt()
        val index = digitGroups.coerceIn(0, birimler.size - 1)
        val deger = bayt / Math.pow(1024.0, index.toDouble())
        return String.format("%.2f %s", deger, birimler[index])
    }

    fun hizDönüştür(baytSaniye: Long): String {
        val formatli = baytDönüştür(baytSaniye)
        return "$formatli/s"
    }

    fun sureFormati(saniye: Long): String {
        if (saniye <= 0) return "Yaklaşık 0 saniye"
        if (saniye < 60) return "Yaklaşık $saniye saniye"
        val dakika = saniye / 60
        val kalanSaniye = saniye % 60
        return "Yaklaşık $dakika dk $kalanSaniye sn"
    }

    fun sha256Hesapla(baytlar: ByteArray): String {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(baytlar)
            digest.joinToString("") { "%02x".format(it) }
        } catch (_: Exception) {
            ""
        }
    }
}
