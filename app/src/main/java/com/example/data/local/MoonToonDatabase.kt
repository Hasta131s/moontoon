package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.*

@Database(
    entities = [
        UserProfile::class,
        FavoriteShow::class,
        WatchHistory::class,
        OfflineDownload::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MoonToonDatabase : RoomDatabase() {
    abstract fun moonToonDao(): MoonToonDao

    companion object {
        @Volatile
        private var INSTANCE: MoonToonDatabase? = null

        fun getDatabase(context: Context): MoonToonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoonToonDatabase::class.java,
                    "moontoon_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
