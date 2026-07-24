package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GospelSphereDao {

    // --- Prayer Network ---
    @Query("SELECT * FROM prayer_requests ORDER BY timestamp DESC")
    fun getAllPrayerRequests(): Flow<List<PrayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerRequest(prayer: PrayerEntity)

    @Query("UPDATE prayer_requests SET prayedCount = prayedCount + 1 WHERE id = :prayerId")
    suspend fun incrementPrayedCount(prayerId: Int)

    // --- Favorites ---
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE mediaId = :mediaId)")
    fun isFavorite(mediaId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE mediaId = :mediaId")
    suspend fun deleteFavorite(mediaId: String)

    // --- Bible Bookmarks ---
    @Query("SELECT * FROM bible_bookmarks ORDER BY timestamp DESC")
    fun getAllBibleBookmarks(): Flow<List<BibleBookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bible_bookmarks WHERE id = :bookmarkId)")
    fun isBibleBookmarked(bookmarkId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBibleBookmark(bookmark: BibleBookmarkEntity)

    @Query("DELETE FROM bible_bookmarks WHERE id = :bookmarkId")
    suspend fun deleteBibleBookmark(bookmarkId: String)
}
