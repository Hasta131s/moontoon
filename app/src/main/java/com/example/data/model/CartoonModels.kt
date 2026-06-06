package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

data class CartoonShow(
    val title: String,
    val searchKeyword: String, // query for OMDB API
    val genres: List<String>,
    val description: String,
    val rating: String = "8.2",
    val years: String = "2010-",
    val seasonsCount: Int = 6,
    val episodes: List<Episode>
)

data class Episode(
    val title: String,
    val url: String,
    val logoUrl: String,
    val season: Int = 1,
    val number: Int = 1
)

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val avatarUrl: String,
    val isKidsMode: Boolean = false,
    val pinCode: String? = null // Child Lock 4-digit PIN
) {
    companion object {
        fun defaultProfiles() = listOf(
            UserProfile(name = "Ana Profil", avatarUrl = "profile_adult", isKidsMode = false),
            UserProfile(name = "Çocuk Modu", avatarUrl = "profile_kids", isKidsMode = true)
        )
    }
}

@Entity(tableName = "favorite_shows", primaryKeys = ["profileId", "showTitle"])
data class FavoriteShow(
    val profileId: Int,
    val showTitle: String
)

@Entity(tableName = "watch_histories")
data class WatchHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val profileId: Int,
    val showTitle: String,
    val episodeTitle: String,
    val episodeUrl: String,
    val logoUrl: String,
    val progressMs: Long = 0,
    val totalDurationMs: Long = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "offline_downloads")
data class OfflineDownload(
    @PrimaryKey val episodeUrl: String,
    val profileId: Int,
    val showTitle: String,
    val episodeTitle: String,
    val localUriString: String,
    val bytesTotal: Long = 0,
    val bytesDownloaded: Long = 0,
    val isCompleted: Boolean = false
)

// OMDB API Models
@JsonClass(generateAdapter = true)
data class OmdbResponse(
    val Title: String? = null,
    val Year: String? = null,
    val Rated: String? = null,
    val Released: String? = null,
    val Runtime: String? = null,
    val Genre: String? = null,
    val Director: String? = null,
    val Writer: String? = null,
    val Actors: String? = null,
    val Plot: String? = null,
    val Language: String? = null,
    val Country: String? = null,
    val Awards: String? = null,
    val Poster: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val totalSeasons: String? = null,
    val Response: String? = null,
    val Error: String? = null
)
