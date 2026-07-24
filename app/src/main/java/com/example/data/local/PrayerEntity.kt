package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_requests")
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val requesterName: String,
    val title: String,
    val content: String,
    val category: String = "General",
    val prayedCount: Int = 1,
    val timestamp: Long = System.currentTimeMillis(),
    val isUserSubmitted: Boolean = false
)
