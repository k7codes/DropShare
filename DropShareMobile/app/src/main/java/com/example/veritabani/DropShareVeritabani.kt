package com.example.veritabani

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TransferGecmisiEntity::class, EslesmisCihazEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DropShareVeritabani : RoomDatabase() {
    abstract fun transferGecmisiDao(): TransferGecmisiDao

    companion object {
        @Volatile
        private var INSTANCE: DropShareVeritabani? = null

        fun veritabaniGetir(context: Context): DropShareVeritabani {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DropShareVeritabani::class.java,
                    "dropshare_vt"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
