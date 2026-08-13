package com.example.veritabani

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferGecmisiDao {
    @Query("SELECT * FROM transfer_gecmisi ORDER BY tarihMs DESC")
    fun tumGecmisiGetir(): Flow<List<TransferGecmisiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun gecmisEkle(gecmis: TransferGecmisiEntity)

    @Query("DELETE FROM transfer_gecmisi")
    suspend fun tumGecmisiTemizle()

    @Query("SELECT * FROM eslesmis_cihazlar ORDER BY eslesmeTarihiMs DESC")
    fun tumEslesmisCihazlariGetir(): Flow<List<EslesmisCihazEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun eslesmisCihazEkle(cihaz: EslesmisCihazEntity)

    @Query("DELETE FROM eslesmis_cihazlar WHERE cihazId = :cihazId")
    suspend fun eslesmisCihazSil(cihazId: String)
}
