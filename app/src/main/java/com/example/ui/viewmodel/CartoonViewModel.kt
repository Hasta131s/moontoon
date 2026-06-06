package com.example.ui.viewmodel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.MainActivity
import com.example.data.local.MoonToonDatabase
import com.example.data.model.*
import com.example.data.repository.CartoonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class CartoonViewModel(application: Application) : AndroidViewModel(application) {

    private val db = MoonToonDatabase.getDatabase(application)
    private val repository = CartoonRepository(db.moonToonDao())

    // --- State Streams ---
    val profiles = repository.allProfiles.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    private val _currentProfile = MutableStateFlow<UserProfile?>(null)
    val currentProfile = _currentProfile.asStateFlow()

    private val _selectedShow = MutableStateFlow<CartoonShow?>(null)
    val selectedShow = _selectedShow.asStateFlow()

    private val _showDetails = MutableStateFlow<OmdbResponse?>(null)
    val showDetails = _showDetails.asStateFlow()

    private val _isLoadingDetail = MutableStateFlow(false)
    val isLoadingDetail = _isLoadingDetail.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Hepsi")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _activeEpisode = MutableStateFlow<Episode?>(null)
    val activeEpisode = _activeEpisode.asStateFlow()

    // Database relations dependent on current profile
    val favorites = _currentProfile.flatMapLatest { profile ->
        if (profile == null) flowOf(emptyList())
        else repository.getFavoritesForProfile(profile.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val watchHistory = _currentProfile.flatMapLatest { profile ->
        if (profile == null) flowOf(emptyList())
        else repository.getWatchHistory(profile.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val downloads = _currentProfile.flatMapLatest { profile ->
        if (profile == null) flowOf(emptyList())
        else repository.getDownloadsForProfile(profile.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Pad Lock State for Kid Mode protection
    private val _isPinScreenActive = MutableStateFlow(false)
    val isPinScreenActive = _isPinScreenActive.asStateFlow()

    private val _pinActionPending = MutableStateFlow<(() -> Unit)?>(null)

    // Fullscreen Loader State
    private val _isAppLoading = MutableStateFlow(true)
    val isAppLoading = _isAppLoading.asStateFlow()

    // --- Curated Data List ---
    private val _showsList = MutableStateFlow<List<CartoonShow>>(emptyList())
    val showsList: StateFlow<List<CartoonShow>> = combine(
        _showsList, _currentProfile, _searchQuery, _selectedCategory
    ) { baseShows, profile, query, category ->
        var list = baseShows

        // Rule: Kids mode active filters out mature or non-kids safe ones
        if (profile?.isKidsMode == true) {
            list = list.filter { show ->
                show.genres.contains("#aile") || show.genres.contains("#çocuk") ||
                        show.genres.contains("#okul") || show.genres.contains("#komedi") ||
                        show.genres.contains("#dostluk") || show.title == "Cartoonito" ||
                        show.title == "Kral Şakir" || show.title == "Masha ve Koca Ayı"
            }
        }

        // Apply Category/Tag Filter
        if (category != "Hepsi") {
            list = list.filter { show -> show.genres.contains(category) }
        }

        // Apply Search Filter
        if (query.isNotBlank()) {
            list = list.filter { show ->
                show.title.contains(query, ignoreCase = true) ||
                        show.description.contains(query, ignoreCase = true) ||
                        show.genres.any { it.contains(query, ignoreCase = true) }
            }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            _isAppLoading.value = true
            repository.seedProfilesIfNeeded()
            // Load base list of shows
            _showsList.value = repository.getCuratedShows()
            delay(1200) // Beautiful cinematic entry animation timer
            // Select default profile
            val initialProfiles = repository.allProfiles.first()
            if (initialProfiles.isNotEmpty()) {
                _currentProfile.value = initialProfiles.first()
            }
            _isAppLoading.value = false
        }
        createNotificationChannel()
    }

    // --- Category Management ---
    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // --- Profile Management ---
    fun createProfile(name: String, avatar: String, isKids: Boolean, pin: String? = null) {
        viewModelScope.launch {
            val newProfile = UserProfile(name = name, avatarUrl = avatar, isKidsMode = isKids, pinCode = pin)
            repository.insertProfile(newProfile)
        }
    }

    fun switchProfile(profile: UserProfile) {
        // Switch security check: profile with PIN lock or exiting kids profile
        val current = _currentProfile.value
        if (current?.isKidsMode == true && !profile.isKidsMode) {
            // Exiting Child Profile requires Kids Lock verification
            requestPinProtection(current) {
                _currentProfile.value = profile
                _selectedShow.value = null
                _activeEpisode.value = null
            }
        } else if (profile.pinCode != null) {
            // Target locked profile requires PIN check
            requestPinProtection(profile) {
                _currentProfile.value = profile
                _selectedShow.value = null
                _activeEpisode.value = null
            }
        } else {
            _currentProfile.value = profile
            _selectedShow.value = null
            _activeEpisode.value = null
        }
    }

    fun deleteProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
        }
    }

    fun updateProfilePin(profileId: Int, pin: String?) {
        viewModelScope.launch {
            repository.updateProfilePin(profileId, pin)
            // Sync active profile state
            val current = _currentProfile.value
            if (current != null && current.id == profileId) {
                _currentProfile.value = current.copy(pinCode = pin)
            }
        }
    }

    // --- Kids Lock Protection ---
    private fun requestPinProtection(profile: UserProfile, onSuccess: () -> Unit) {
        _pinActionPending.value = onSuccess
        _isPinScreenActive.value = true
    }

    fun submitLockPin(profile: UserProfile, pin: String): Boolean {
        return if (profile.pinCode == pin || pin == "8888") { // 8888 is general Master PIN Code
            _isPinScreenActive.value = false
            _pinActionPending.value?.invoke()
            _pinActionPending.value = null
            true
        } else {
            false
        }
    }

    fun cancelPinChallenge() {
        _isPinScreenActive.value = false
        _pinActionPending.value = null
    }

    // --- Show & OMDB Details ---
    fun selectShow(show: CartoonShow) {
        _selectedShow.value = show
        _showDetails.value = null
        _isLoadingDetail.value = true

        viewModelScope.launch {
            val response = repository.fetchShowDetails(show.searchKeyword)
            _showDetails.value = response
            _isLoadingDetail.value = false
        }
    }

    fun clearSelectedShow() {
        _selectedShow.value = null
        _showDetails.value = null
    }

    // --- Favorites ---
    fun toggleFavorite(showTitle: String) {
        val profile = _currentProfile.value ?: return
        viewModelScope.launch {
            repository.toggleFavorite(profile.id, showTitle)
        }
    }

    fun isShowFavorite(showTitle: String): Flow<Boolean> {
        val profile = _currentProfile.value ?: return flowOf(false)
        return repository.isFavoriteFlow(profile.id, showTitle)
    }

    // --- Video Player & History ---
    fun playEpisode(episode: Episode) {
        _activeEpisode.value = episode
        saveEpisodeProgress(episode, 0)
    }

    fun closePlayer() {
        _activeEpisode.value = null
    }

    fun saveEpisodeProgress(episode: Episode, progressMs: Long, totalDurationMs: Long = 600000) {
        val profile = _currentProfile.value ?: return
        val show = _selectedShow.value ?: return
        viewModelScope.launch {
            repository.addWatchHistory(
                WatchHistory(
                    profileId = profile.id,
                    showTitle = show.title,
                    episodeTitle = episode.title,
                    episodeUrl = episode.url,
                    logoUrl = episode.logoUrl,
                    progressMs = progressMs,
                    totalDurationMs = totalDurationMs
                )
            )
        }
    }

    fun clearAllHistory() {
        val profile = _currentProfile.value ?: return
        viewModelScope.launch {
            repository.clearWatchHistory(profile.id)
        }
    }

    fun deleteWatchHistoryItem(profileId: Int, episodeUrl: String) {
        viewModelScope.launch {
            repository.deleteWatchHistoryItem(profileId, episodeUrl)
        }
    }

    // --- Simulated Download Engine ---
    fun deleteDownload(episodeUrl: String) {
        viewModelScope.launch {
            repository.deleteDownload(episodeUrl)
        }
    }

    fun toggleEpisodeDownload(episode: Episode) {
        val profile = _currentProfile.value ?: return
        val show = _selectedShow.value ?: return
        val exist = downloads.value.find { it.episodeUrl == episode.url }

        if (exist != null) {
            // Delete download
            viewModelScope.launch {
                repository.deleteDownload(episode.url)
            }
        } else {
            // Start download progress simulation
            viewModelScope.launch {
                val initial = OfflineDownload(
                    episodeUrl = episode.url,
                    profileId = profile.id,
                    showTitle = show.title,
                    episodeTitle = episode.title,
                    localUriString = "",
                    bytesTotal = 150_000_000,
                    bytesDownloaded = 0,
                    isCompleted = false
                )
                repository.addOrUpdateDownload(initial)

                // Simulate incremental progress
                for (i in 5..100 step 15) {
                    delay(800)
                    val progress = initial.copy(
                        bytesDownloaded = (initial.bytesTotal * i / 100),
                        isCompleted = i >= 100,
                        localUriString = if (i >= 100) "content://moontoon/downloads/${UUID.randomUUID()}" else ""
                    )
                    repository.addOrUpdateDownload(progress)
                    if (downloads.value.none { it.episodeUrl == episode.url }) {
                        // Cancelled during progress
                        break
                    }
                }
            }
        }
    }

    // --- Notification System (+40 cartoon quotes invitations) ---
    private val notificationChannelId = "moontoon_notifications"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MoonToon Eğlenceli Bildirimler"
            val descriptionText = "Youtuber Necati, Necati, Gumball ve sevimli karakterlerden davetler!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationChannelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun triggerRandomSystemNotification() {
        val context = getApplication<Application>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check permission: in Android 13+ we request notification permission, but for maximum robustness,
            // we will build matching fallback list in UI and post if allowed. Or we post and trigger inside system.
        }

        val inviteQuotes = listOf(
            "Fil Necati: Moritanya'dan özel dürüm geldi, MoonToon'da dürümleri yiyip Gumball izliyoruz, koş! 🌯📺",
            "Gumball: Okulda ders kaynatmak bir sanattır, ama MoonToon'da çizgi dizi izlemek bir yaşam tarzıdır! 🎓🐾",
            "Darwin: Gumball yine bir işler peşinde ve bu sefer bir balık olarak bacaklarım titriyor! İzle! 🐟👣",
            "Aslan Remzi: Necati televizyonun kumandasını yedi! Şakir yardım et, MoonToon'da yeni bölüm başlamış! 🦁📺",
            "Mojo Jojo: Townsville'i yok etmek için harika bir planım vardı fakat MoonToon'da Powerpuff Girls başladı! 🐵🔬",
            "Johnny Bravo: Hey tatlı bebek! Şunları görüyor musun? MoonToon'da kaslarımı sergiliyorum, kaçırma! 😎💪",
            "Steve Amca: GÜNAYDIN! Karavanımla MoonToon ülkesine geldim, her şey çok acayip! 🍕🚘",
            "Finn & Jake: Macera zamanı! Jake kılıç şeklini aldı, m3u8 dalgalarını kesmeye hazırız! ⚔️🐕",
            "Ben 10: Omnitrix yine yanlış uzaylıyı seçti! Şimşek Hız olmak yerine MoonToon izlemeye karar verdim! 👽⌚",
            "Şakir: Necati Abi bilgisayarın içine kaçtı, onu kurtarmak için tıklamanız gerekiyor! 🎮🖥️",
            "Mordecai & Rigby: Hey ahbap! Benson bizi işten atmadan önce son bir bölüm MoonToon izleyeli mi? 🐦🦝",
            "Kas Adam: Bu dünyadaki en komik MoonToon şakasını kim yapar biliyor musun? ANNEM! 👩💥",
            "Raven: Karanlık odamda kitap okumaktan sıkıldım. MoonToon izleyip dünyayı kurtaracağız. 💜🔮",
            "Clarence: Sumo kaktüse sarıldı! Jeff hijyen krizi geçiriyor! Bu çılgınlığı izlemelisin! 🌵👦",
            "44 Kedi - Lampo: Gitarımı buldum, kedi grubu toplandı! Konser MoonToon'da başlıyor! 🐱🎸",
            "Winx Club - Bloom: Alfea okulu bu hafta tatil! Winx perileri MoonToon'da parti yapıyor! 🧚‍♀️💖",
            "Fil Necati: Hayat bir dürümdür, dürümleri yemeyen üzgündür. MoonToon'da dürüm şenliği başladı! 🐘🌯",
            "Angela: Sınıfın en büyük planını yaptım! Bu planı bozmamak için hemen uygulamaya gir! 🎒🤓",
            "Masha: Koca Ayı uyuyor! Onu uyandırmadan MoonToon'da gizlice macera izleyelim! 👧🐻",
            "Ninjago - Kai: Spinjitzu gücünü sergiliyoruz! Ejderhaya binip MoonToon izle! 🐉🔥",
            "Unikitty: Dünyada üzcü şeylere yer yok! Her yer gökkuşağı ve MoonToon pembeliği! 🦄🌈",
            "Patron Bebek: Toplantı iptal! MoonToon'da süt şişeleriyle yeni çizgi dizi izliyoruz! 👶🍼",
            "Dexter: Dee Dee laboratuvarımı patlattı! MoonToon'da kendime yeni bir sığınak kurdum. 🔬🧪",
            "Kral Şakir - Kürdan Şakir: Canan yine çok bilmişlik yapıyor, Necati Abi ise uzağa fırladı! 🐾☄️",
            "Johnny Test: Dukey konuşmaya başladı! Laboratuvarda zamanı durduran saati bulduk! 🐕💡",
            "Kamp Lazlo: Salyangoz festivalini kim kaçırır? Barbunya Kampı MoonToon'da yayında! 🏕️🌲",
            "Cesur Prens Ivandoe: Ben dünyanın en cesur geyiğiyim! Sadık yaverim Bert bana MoonToon açtı! 🦌🛡️",
            "Gormiti: Elementlerin gücü adına! Meka Şövalyesi Kyonos MoonToon'da savaşa hazır! ❄️🛡️",
            "Ben 10 - Gri Madde: Akıllı olmak zordur ama MoonToon'dan m3u8 izlemek çok pratiktir! 🧠🧬",
            "Fil Necati: Dünyayı gezdim dolaştım, MoonToon'un minimalist ve şık tasarımı kadar güzel dürüm görmedim! 🪐🌯",
            "Gumball - Anais: Gumball ve Darwin yine benden gizli plan yapıyor. Onları MoonToon'da izliyorum! 🐰🧐",
            "Regular Show - Benson: Mordecai, Rigby! MoonToon'u kapatıp hemen parktaki yaprakları süpürün! yoksa KOVULDUNUZ! 😡🧹",
            "Teen Titans - Beast Boy: Turta festivaline davetlisiniz! MoonToon'da turta yemeye geldik! 🥧🍕",
            "We Bare Bears - Panda: Telefonumun şarjı bitiyor ama MoonToon'daki yeni bölümü bitirmeden bırakmam! 🐼📱",
            "Fil Necati: Kürdan Şakir! Kadriye Hanıma söyle bize MoonToon'dan Gumball açsın, çorba soğuyor! 🥣🐾",
            "Masha: Koca Ayı, bana MoonToon aç, yoksa her yeri dağıtırım ki! 👧🐻💥",
            "Powerpuff - Buttercup: Ne cici bici kızlar mı? Hayır, biz Townsville'i sallıyoruz, MoonToon'dayız! 💥👊",
            "Johnny Bravo: Hey şatık, bu akşam ne kadar havalıyım? MoonToon'da kendimi izliyorum. 😎🌟",
            "Adalar Canavarı Flapjack: Nane Şekeri Adası'na gidiyoruz, MoonToon fıçılarını hazırla! 🍬🛶",
            "Kamp Lazlo - Lumpus: Barbunya Kampı'nın en lüks kasedini açtım! MoonToon'da toplanın! 🦌🏕️",
            "Kral Şakir - Necati: Kankametreyle Şakir'i test ettim, kankalık puanı MoonToon izleyince 100 oldu! 🐘🎯"
        )

        val randomInvite = inviteQuotes.random()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(android.R.drawable.presence_video_online) // premium feel default icon
            .setContentTitle("MoonToon Daveti 🚀")
            .setContentText(randomInvite)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(randomInvite))
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            try {
                notify(System.currentTimeMillis().toInt(), builder.build())
            } catch (e: SecurityException) {
                // Permission not granted on 13+ (we'll show on-screen Toast or dialog in-app)
                e.printStackTrace()
            }
        }
    }
}
