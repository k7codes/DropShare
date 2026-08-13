package com.example.ag

import android.content.Context
import android.os.Environment
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
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder

class WebDropSunucusu(private val context: Context) {

    private val PORT = 8080
    private val scope = CoroutineScope(Dispatchers.IO)
    private var webJob: Job? = null
    private var serverSocket: ServerSocket? = null

    private val _calisiyor = MutableStateFlow(false)
    val calisiyor: StateFlow<Boolean> = _calisiyor.asStateFlow()

    private val _webGelenMetin = MutableSharedFlow<String>()
    val webGelenMetin: SharedFlow<String> = _webGelenMetin.asSharedFlow()

    fun sunucuyuBaslat(cihazAdi: String) {
        if (webJob?.isActive == true) return

        webJob = scope.launch {
            try {
                serverSocket = ServerSocket(PORT)
                _calisiyor.value = true

                while (isActive) {
                    val client = serverSocket?.accept() ?: break
                    scope.launch {
                        handleHttpRequest(client, cihazAdi)
                    }
                }
            } catch (_: Exception) {
            } finally {
                _calisiyor.value = false
                serverSocket?.close()
            }
        }
    }

    private suspend fun handleHttpRequest(socket: Socket, cihazAdi: String) = withContext(Dispatchers.IO) {
        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))

        try {
            val requestLine = reader.readLine() ?: return@withContext
            val parts = requestLine.split(" ")
            if (parts.size < 2) return@withContext

            val method = parts[0]
            val path = parts[1]

            var contentLength = 0
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line.isNull_or_Empty()) break
                if (line!!.startsWith("Content-Length:", ignoreCase = true)) {
                    contentLength = line!!.substringAfter(":").trim().toIntOrNull() ?: 0
                }
            }

            if (method == "GET" && (path == "/" || path.startsWith("/?"))) {
                val html = htmlArayuzuOlustur(cihazAdi)
                httpYanitiGonder(outputStream, "200 OK", "text/html; charset=utf-8", html.toByteArray())
            } else if (method == "GET" && path.startsWith("/indir")) {
                val dosyaAdiParam = path.substringAfter("dosya=", "").substringBefore("&")
                val dosyaAdi = URLDecoder.decode(dosyaAdiParam, "UTF-8")
                val indirmeDizini = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "DropShare"
                )
                val dosya = File(indirmeDizini, dosyaAdi)
                if (dosya.exists()) {
                    val fileBytes = FileInputStream(dosya).use { it.readBytes() }
                    httpYanitiGonder(outputStream, "200 OK", "application/octet-stream", fileBytes, dosya.name)
                } else {
                    val html = "<h1>404 - Dosya Bulunamadi</h1>"
                    httpYanitiGonder(outputStream, "404 Not Found", "text/html", html.toByteArray())
                }
            } else if (method == "POST" && path == "/yukle") {
                // Basit binary veya form yükleme
                if (contentLength > 0) {
                    val buffer = ByteArray(4096)
                    val indirmeDizini = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "DropShare"
                    )
                    if (!indirmeDizini.exists()) indirmeDizini.mkdirs()

                    val hedef = File(indirmeDizini, "WebDrop_${System.currentTimeMillis()}.bin")
                    val fos = FileOutputStream(hedef)
                    var kalan = contentLength
                    while (kalan > 0) {
                        val okunan = inputStream.read(buffer, 0, buffer.size.coerceAtMost(kalan))
                        if (okunan == -1) break
                        fos.write(buffer, 0, okunan)
                        kalan -= okunan
                    }
                    fos.close()
                }

                val yanit = "<html><body style='background:#0f172a;color:#fff;font-family:sans-serif;text-align:center;padding:50px;'><h2>✅ Dosya Başarıyla Yüklendi!</h2><a href='/' style='color:#06b6d4;'>Geri Dön</a></body></html>"
                httpYanitiGonder(outputStream, "200 OK", "text/html; charset=utf-8", yanit.toByteArray())
            } else {
                val html = "<h1>404 - DropShare Web Drop</h1>"
                httpYanitiGonder(outputStream, "404 Not Found", "text/html", html.toByteArray())
            }
        } catch (_: Exception) {
        } finally {
            socket.close()
        }
    }

    private fun String?.isNull_or_Empty(): Boolean = this == null || this.trim().isEmpty()

    private fun httpYanitiGonder(
        outputStream: OutputStream,
        status: String,
        contentType: String,
        data: ByteArray,
        downloadFilename: String? = null
    ) {
        val sb = StringBuilder()
        sb.append("HTTP/1.1 $status\r\n")
        sb.append("Content-Type: $contentType\r\n")
        sb.append("Content-Length: ${data.size}\r\n")
        if (downloadFilename != null) {
            sb.append("Content-Disposition: attachment; filename=\"$downloadFilename\"\r\n")
        }
        sb.append("Connection: close\r\n")
        sb.append("\r\n")

        outputStream.write(sb.toString().toByteArray())
        outputStream.write(data)
        outputStream.flush()
    }

    private fun htmlArayuzuOlustur(cihazAdi: String): String {
        val indirmeDizini = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "DropShare"
        )
        val dosyalar = indirmeDizini.listFiles()?.filter { it.isFile }?.take(10) ?: emptyList()

        val dosyaListesiHtml = StringBuilder()
        if (dosyalar.isEmpty()) {
            dosyaListesiHtml.append("<p style='color:#94a3b8;'>Henüz cihazda paylaşılan dosya yok.</p>")
        } else {
            dosyaListesiHtml.append("<ul style='list-style:none;padding:0;'>")
            for (f in dosyalar) {
                val boyut = AagYardimcisi.baytDönüştür(f.length())
                val urlName = java.net.URLEncoder.encode(f.name, "UTF-8")
                dosyaListesiHtml.append("""
                    <li style='background:#1e293b;margin:8px 0;padding:12px 16px;border-radius:10px;display:flex;justify-content:space-between;align-items:center;'>
                        <span style='font-weight:600;'>📄 ${f.name} <small style='color:#94a3b8;'>($boyut)</small></span>
                        <a href='/indir?dosya=$urlName' style='background:#06b6d4;color:#0f172a;padding:6px 14px;border-radius:6px;text-decoration:none;font-weight:bold;'>İndir</a>
                    </li>
                """.trimIndent())
            }
            dosyaListesiHtml.append("</ul>")
        }

        return """
            <!DOCTYPE html>
            <html lang="tr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>DropShare Web Drop</title>
                <style>
                    body { background-color: #0f172a; color: #f8fafc; font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; margin: 0; padding: 20px; }
                    .container { max-width: 650px; margin: 0 auto; }
                    .card { background: #1e293b; border-radius: 16px; padding: 24px; margin-bottom: 20px; border: 1px solid #334155; }
                    .header { text-align: center; margin-bottom: 30px; }
                    .header h1 { color: #06b6d4; margin: 0; font-size: 28px; }
                    .header p { color: #94a3b8; margin-top: 5px; }
                    .badge { background: #164e63; color: #cffafe; padding: 4px 12px; border-radius: 20px; font-size: 13px; font-weight: bold; }
                    input[type=file] { margin: 15px 0; display: block; color: #94a3b8; }
                    .btn { background: #06b6d4; color: #0f172a; border: none; padding: 12px 24px; border-radius: 8px; font-weight: bold; cursor: pointer; font-size: 16px; width: 100%; }
                    .footer { text-align: center; color: #64748b; font-size: 12px; margin-top: 30px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>DROPShare Web Drop</h1>
                        <p>Cihaz: <span class="badge">$cihazAdi</span></p>
                    </div>

                    <div class="card">
                        <h3 style="margin-top:0;color:#14b8a6;">📤 Telefona Dosya Gönder</h3>
                        <form action="/yukle" method="POST" enctype="multipart/form-data">
                            <input type="file" name="dosya" required />
                            <button type="submit" class="btn">Dosyayı Gönder</button>
                        </form>
                    </div>

                    <div class="card">
                        <h3 style="margin-top:0;color:#06b6d4;">📥 Telefonda İndirilebilir Dosyalar</h3>
                        $dosyaListesiHtml
                    </div>

                    <div class="footer">
                        DropShare Yerel Ağ Dosya Aktarımı • Developed By K7~
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun sunucuyuDurdur() {
        webJob?.cancel()
        _calisiyor.value = false
        try {
            serverSocket?.close()
        } catch (_: Exception) {}
    }
}
