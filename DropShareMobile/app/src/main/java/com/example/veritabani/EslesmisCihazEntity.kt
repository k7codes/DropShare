package com.example.veritabani

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eslesmis_cihazlar")
data class EslesmisCihazEntity(
    @PrimaryKey val cihazId: String,
    val cihazAdi: String,
    val cihazTuru: String,
    val sonIp: String,
    val eslesmeTarihiMs: Long = System.currentTimeMillis(),
    val gizliAnahtar: String = ""
)
