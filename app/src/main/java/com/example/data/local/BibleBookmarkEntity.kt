package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bible_bookmarks")
data class BibleBookmarkEntity(
    @PrimaryKey val id: String, // e.g., "John_3_16"
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val note: String = "",
    val isHighlighted: Boolean = false,
    val highlightColorHex: String = "#FEE2E2",
    val timestamp: Long = System.currentTimeMillis()
)
