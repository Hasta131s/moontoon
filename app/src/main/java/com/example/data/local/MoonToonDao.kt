package com.example.data.local

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoonToonDao {

    // --- User Profiles ---
    @Query("SELECT * FROM user_profiles ORDER BY id ASC")
    fun getAllProfiles(): Flow<List<UserProfile>>

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    suspend fun getProfileById(id: Int): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile)

    @Update
    suspend fun updateProfile(profile: UserProfile)

    @Delete
    suspend fun deleteProfile(profile: UserProfile)

    @Query("UPDATE user_profiles SET pinCode = :pin WHERE id = :id")
    suspend fun updateProfilePin(id: Int, pin: String?)

    // --- Favorites ---
    @Query("SELECT * FROM favorite_shows WHERE profileId = :profileId")
    fun getFavoritesForProfile(profileId: Int): Flow<List<FavoriteShow>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_shows WHERE profileId = :profileId AND showTitle = :showTitle)")
    fun isFavoriteFlow(profileId: Int, showTitle: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favorite_shows WHERE profileId = :profileId AND showTitle = :showTitle)")
    suspend fun isFavoriteSuspend(profileId: Int, showTitle: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteShow: FavoriteShow)

    @Delete
    suspend fun deleteFavorite(favoriteShow: FavoriteShow)

    // --- Watch History ---
    @Query("SELECT * FROM watch_histories WHERE profileId = :profileId ORDER BY timestamp DESC")
    fun getWatchHistory(profileId: Int): Flow<List<WatchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchHistory(watchHistory: WatchHistory)

    @Query("DELETE FROM watch_histories WHERE profileId = :profileId AND episodeUrl = :episodeUrl")
    suspend fun deleteWatchHistoryItem(profileId: Int, episodeUrl: String)

    @Query("DELETE FROM watch_histories WHERE profileId = :profileId")
    suspend fun clearWatchHistory(profileId: Int)

    // --- Offline Downloads ---
    @Query("SELECT * FROM offline_downloads WHERE profileId = :profileId")
    fun getDownloadsForProfile(profileId: Int): Flow<List<OfflineDownload>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDownload(offlineDownload: OfflineDownload)

    @Query("DELETE FROM offline_downloads WHERE episodeUrl = :episodeUrl")
    suspend fun deleteDownload(episodeUrl: String)
}
