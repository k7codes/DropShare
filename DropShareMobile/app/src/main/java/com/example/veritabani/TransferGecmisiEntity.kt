package com.example.veritabani

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfer_gecmisi")
data class TransferGecmisiEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dosyaAdi: String,
    val dosyaBoyutu: Long,
    val dosyaTuru: String,
    val hedefKaynakCihaz: String,
    val yon: String, // "GONDERILDI" veya "ALINDI"
    val durum: String, // "BASARILI", "BASARISIZ", "REDDEDILDI", "IPTAL"
    val tarihMs: Long = System.currentTimeMillis(),
    val sha256Hash: String = ""
)
