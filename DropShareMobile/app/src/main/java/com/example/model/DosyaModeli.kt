package com.example.model

import android.net.Uri

data class DosyaModeli(
    val uri: Uri?,
    val dosyaAdi: String,
    val dosyaBoyutu: Long,
    val mimeType: String = "*/*",
    val sha256Hash: String = "",
    val isKlasor: Boolean = false,
    val dosyaYolu: String = ""
)
