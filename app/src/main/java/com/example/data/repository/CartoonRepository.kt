package com.example.data.repository

import com.example.data.local.MoonToonDao
import com.example.data.model.*
import com.example.data.remote.OmdbService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CartoonRepository(
    private val moonToonDao: MoonToonDao,
    private val omdbService: OmdbService = OmdbService.create()
) {
    // Curated cartoons static list
    fun getCuratedShows(): List<CartoonShow> = CartoonData.getShows()

    // Fetch details dynamically from OMDB API
    suspend fun fetchShowDetails(title: String): OmdbResponse {
        return try {
            omdbService.getShowDetails(title = title)
        } catch (e: Exception) {
            e.printStackTrace()
            // Provide localized failure fallback
            OmdbResponse(
                Title = title,
                Plot = "Detaylar şu anda yüklenemedi. İnternet bağlantınızı kontrol edin.",
                imdbRating = "8.2",
                Response = "False",
                Error = e.message
            )
        }
    }

    // --- User Profiles ---
    val allProfiles: Flow<List<UserProfile>> = moonToonDao.getAllProfiles()

    suspend fun getProfileById(id: Int): UserProfile? = moonToonDao.getProfileById(id)

    suspend fun insertProfile(profile: UserProfile) = moonToonDao.insertProfile(profile)

    suspend fun updateProfile(profile: UserProfile) = moonToonDao.updateProfile(profile)

    suspend fun deleteProfile(profile: UserProfile) = moonToonDao.deleteProfile(profile)

    suspend fun updateProfilePin(id: Int, pin: String?) = moonToonDao.updateProfilePin(id, pin)

    // Seed default profiles if not present
    suspend fun seedProfilesIfNeeded() {
        val existing = allProfiles.first()
        if (existing.isEmpty()) {
            for (profile in UserProfile.defaultProfiles()) {
                moonToonDao.insertProfile(profile)
            }
        }
    }

    // --- Favorites ---
    fun getFavoritesForProfile(profileId: Int): Flow<List<FavoriteShow>> =
        moonToonDao.getFavoritesForProfile(profileId)

    fun isFavoriteFlow(profileId: Int, showTitle: String): Flow<Boolean> =
        moonToonDao.isFavoriteFlow(profileId, showTitle)

    suspend fun toggleFavorite(profileId: Int, showTitle: String) {
        val favorite = FavoriteShow(profileId = profileId, showTitle = showTitle)
        if (moonToonDao.isFavoriteSuspend(profileId, showTitle)) {
            moonToonDao.deleteFavorite(favorite)
        } else {
            moonToonDao.insertFavorite(favorite)
        }
    }

    // --- Watch History ---
    fun getWatchHistory(profileId: Int): Flow<List<WatchHistory>> =
        moonToonDao.getWatchHistory(profileId)

    suspend fun addWatchHistory(watchHistory: WatchHistory) {
        moonToonDao.insertWatchHistory(watchHistory)
    }

    suspend fun deleteWatchHistoryItem(profileId: Int, episodeUrl: String) {
        moonToonDao.deleteWatchHistoryItem(profileId, episodeUrl)
    }

    suspend fun clearWatchHistory(profileId: Int) {
        moonToonDao.clearWatchHistory(profileId)
    }

    // --- Offline Downloads ---
    fun getDownloadsForProfile(profileId: Int): Flow<List<OfflineDownload>> =
        moonToonDao.getDownloadsForProfile(profileId)

    suspend fun addOrUpdateDownload(download: OfflineDownload) {
        moonToonDao.insertOrUpdateDownload(download)
    }

    suspend fun deleteDownload(episodeUrl: String) {
        moonToonDao.deleteDownload(episodeUrl)
    }
}
