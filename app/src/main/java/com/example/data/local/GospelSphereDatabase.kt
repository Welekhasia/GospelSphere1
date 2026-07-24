package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PrayerEntity::class,
        FavoriteEntity::class,
        BibleBookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GospelSphereDatabase : RoomDatabase() {

    abstract fun gospelSphereDao(): GospelSphereDao

    companion object {
        @Volatile
        private var INSTANCE: GospelSphereDatabase? = null

        fun getDatabase(context: Context): GospelSphereDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GospelSphereDatabase::class.java,
                    "gospelsphere_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
