package com.example.data.repository

import com.example.data.local.BibleBookmarkEntity
import com.example.data.local.FavoriteEntity
import com.example.data.local.GospelSphereDao
import com.example.data.local.PrayerEntity
import com.example.data.model.SampleGospelData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GospelSphereRepository(private val dao: GospelSphereDao) {

    init {
        // Seed initial prayer requests if database is fresh
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val current = dao.getAllPrayerRequests().first()
                if (current.isEmpty()) {
                    SampleGospelData.initialPrayerRequests.forEach { prayer ->
                        dao.insertPrayerRequest(prayer)
                    }
                }
            } catch (e: Exception) {
                // Ignore initialization errors
            }
        }
    }

    // --- Prayer Wall ---
    val allPrayers: Flow<List<PrayerEntity>> = dao.getAllPrayerRequests()

    suspend fun addPrayerRequest(title: String, requester: String, content: String, category: String) {
        val newPrayer = PrayerEntity(
            requesterName = requester.ifBlank { "Anonymous Believer" },
            title = title,
            content = content,
            category = category,
            prayedCount = 1,
            isUserSubmitted = true
        )
        dao.insertPrayerRequest(newPrayer)
    }

    suspend fun incrementPrayed(prayerId: Int) {
        dao.incrementPrayedCount(prayerId)
    }

    // --- Favorites ---
    val allFavorites: Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    fun isFavorite(mediaId: String): Flow<Boolean> = dao.isFavorite(mediaId)

    suspend fun toggleFavorite(mediaId: String, type: String, title: String, subtitle: String, imageUrl: String = "") {
        val currentlyFav = dao.isFavorite(mediaId).first()
        if (currentlyFav) {
            dao.deleteFavorite(mediaId)
        } else {
            val fav = FavoriteEntity(
                mediaId = mediaId,
                mediaType = type,
                title = title,
                subtitle = subtitle,
                imageUrl = imageUrl
            )
            dao.insertFavorite(fav)
        }
    }

    // --- Bible Bookmarks ---
    val allBookmarks: Flow<List<BibleBookmarkEntity>> = dao.getAllBibleBookmarks()

    fun isBookmarked(bookmarkId: String): Flow<Boolean> = dao.isBibleBookmarked(bookmarkId)

    suspend fun toggleBookmark(book: String, chapter: Int, verse: Int, text: String, note: String = "") {
        val id = "${book}_${chapter}_$verse"
        val exists = dao.isBibleBookmarked(id).first()
        if (exists) {
            dao.deleteBibleBookmark(id)
        } else {
            val bookmark = BibleBookmarkEntity(
                id = id,
                book = book,
                chapter = chapter,
                verse = verse,
                text = text,
                note = note
            )
            dao.insertBibleBookmark(bookmark)
        }
    }
}
