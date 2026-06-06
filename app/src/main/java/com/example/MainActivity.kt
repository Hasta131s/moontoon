package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewmodel.compose.viewModel
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserProfile
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.ElectricTeal
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PureVoid
import com.example.ui.theme.SlateCard
import com.example.ui.viewmodel.CartoonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Request Notification permission for Android 13+ to support funny cartoon quotes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        setContent {
            MyApplicationTheme {
                val viewModel: CartoonViewModel = viewModel()
                val isAppLoading by viewModel.isAppLoading.collectAsStateWithLifecycle()

                if (isAppLoading) {
                    FullscreenLoader(text = "MoonToon'a Giriş Yapılıyor...")
                } else {
                    AppContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppContent(viewModel: CartoonViewModel) {
    val profiles by viewModel.profiles.collectAsStateWithLifecycle()
    val currentProfile by viewModel.currentProfile.collectAsStateWithLifecycle()
    val isPinScreenActive by viewModel.isPinScreenActive.collectAsStateWithLifecycle()

    val activeEpisode by viewModel.activeEpisode.collectAsStateWithLifecycle()
    val selectedShow by viewModel.selectedShow.collectAsStateWithLifecycle()
    val showDetails by viewModel.showDetails.collectAsStateWithLifecycle()
    val isLoadingDetail by viewModel.isLoadingDetail.collectAsStateWithLifecycle()

    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val watchHistory by viewModel.watchHistory.collectAsStateWithLifecycle()
    val downloads by viewModel.downloads.collectAsStateWithLifecycle()

    val showsList by viewModel.showsList.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    var activeTab by remember { mutableIntStateOf(0) } // 0: Home, 1: Search, 2: Library, 3: Downloads, 4: Profiles
    val context = LocalContext.current

    // Base Shows lists for Hero highlights
    val baseShowsList = remember { viewModel.getShowsList() ?: emptyList() } // fallback matching list

    // --- Pad Lock dialog trigger ---
    if (isPinScreenActive && currentProfile != null) {
        KidsLockPinDialog(
            profile = currentProfile!!,
            onPinDismiss = { viewModel.cancelPinChallenge() },
            onSubmitPin = { pin ->
                val ok = viewModel.submitLockPin(currentProfile!!, pin)
                if (!ok) {
                    Toast.makeText(context, "Hatalı Şifre! Giriş Engellendi.", Toast.LENGTH_SHORT).show()
                }
                ok
            }
        )
    }

    // --- Active Streaming Video Player Override ---
    if (activeEpisode != null) {
        CustomPlayer(
            episode = activeEpisode!!,
            onClosePlayer = { viewModel.closePlayer() },
            onProgressUpdate = { pos, dur ->
                viewModel.saveEpisodeProgress(activeEpisode!!, pos, dur)
            }
        )
        return
    }

    // --- Profile Landing Override (Required profile selection first on boot) ---
    if (currentProfile == null) {
        ProfileScreen(
            profiles = profiles,
            onProfileSelected = { prof -> viewModel.switchProfile(profile = prof) },
            onCreateProfile = { name, avatar, isKids, pin ->
                viewModel.createProfile(name, avatar, isKids, pin)
                Toast.makeText(context, "$name profili oluşturuldu!", Toast.LENGTH_SHORT).show()
            },
            onDeleteProfile = { prof -> viewModel.deleteProfile(prof) }
        )
        return
    }

    // --- Home Shell Master Scaffold Layout ---
    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                activeTab = activeTab,
                onTabSelect = { index ->
                    activeTab = index
                    // Clear search / push stacks on main navigation triggers
                    viewModel.setSearchQuery("")
                    viewModel.clearSelectedShow()
                }
            )
        },
        containerColor = MidnightBlue
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Push-Screen: Card details view override
            if (selectedShow != null) {
                val isFavSubState = favorites.any { it.showTitle == selectedShow!!.title }
                DetailScreen(
                    show = selectedShow!!,
                    omdbDetails = showDetails,
                    isLoadingOmdb = isLoadingDetail,
                    isFavorite = isFavSubState,
                    onToggleFavorite = { viewModel.toggleFavorite(selectedShow!!.title) },
                    downloads = downloads,
                    onToggleDownload = { ep -> viewModel.toggleEpisodeDownload(ep) },
                    onPlayEpisode = { ep -> viewModel.playEpisode(ep) },
                    onBack = { viewModel.clearSelectedShow() }
                )
            } else {
                // Secondary main navigation tabs swapper
                when (activeTab) {
                    0 -> HomeScreen(
                        shows = listOf(showsList),
                        curatedShows = showsList,
                        activeProfile = currentProfile,
                        recentWatchList = watchHistory,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { q -> viewModel.setSearchQuery(it = q) },
                        selectedCategory = selectedCategory,
                        onCategorySelected = { cat -> viewModel.setCategory(cat) },
                        onShowSelected = { s -> viewModel.selectShow(s) },
                        onNotificationTrigger = {
                            viewModel.triggerRandomSystemNotification()
                            Toast.makeText(context, "Eğlenceli davet bildirimi gönderildi!", Toast.LENGTH_SHORT).show()
                        }
                    )
                    1 -> SearchScreen(
                        query = searchQuery,
                        onQueryChange = { q -> viewModel.setSearchQuery(q) },
                        showsList = showsList,
                        onShowSelect = { s -> viewModel.selectShow(s) }
                    )
                    2 -> LibraryScreen(
                        favorites = favorites,
                        history = watchHistory,
                        curatedShows = baseShowsList,
                        onShowSelect = { s -> viewModel.selectShow(s) },
                        onClearHistory = { viewModel.clearAllHistory() },
                        onDeleteHistoryItem = { u -> viewModel.deleteWatchHistoryItem(currentProfile!!.id, u) }
                    )
                    3 -> DownloadScreen(
                        downloads = downloads,
                        curatedShows = baseShowsList,
                        onPlayOffline = { ep -> viewModel.playEpisode(ep) },
                        onDeleteDownload = { u -> viewModel.deleteDownload(u) }
                    )
                    4 -> ProfileManagementSubTab(
                        profile = currentProfile!!,
                        profiles = profiles,
                        onSwitchProfile = { prof -> viewModel.switchProfile(prof) },
                        onUpdatePin = { p -> viewModel.updateProfilePin(currentProfile!!.id, p) },
                        onLogout = {
                            // Logout profile, returns to landing profile portal
                            viewModel.switchProfile(UserProfile(id = -1, name = "", avatarUrl = ""))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileManagementSubTab(
    profile: UserProfile,
    profiles: List<UserProfile>,
    onSwitchProfile: (UserProfile) -> Unit,
    onUpdatePin: (String?) -> Unit,
    onLogout: () -> Unit
) {
    var newPinText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil Yönetimi ve Güvenlik",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .statusBarsPadding()
        )

        // Active Profile details info card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(ElectricTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = profile.name.take(1).uppercase(),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = profile.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (profile.isKidsMode) "Çocuk Modu (Kısıtlı İzleme)" else "Yetişkin Modu (Tam Erişim)",
                        color = ElectricTeal,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Child lock PIN setup area
        Text(
            text = "Veli Şifresi (Child Lock PIN)",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Bu profil veya çocuk profilinden çıkışı kilitlemek için 4 haneli bir veli PIN şifresi belirleyebilirsiniz.",
                    color = AccentGray,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPinText,
                        onValueChange = { if (it.length <= 4) newPinText = it },
                        placeholder = { Text("Yeni PIN (Örn: 1234)", color = Color.White.copy(alpha = 0.4f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricTeal,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1.5f)
                    )

                    Button(
                        onClick = {
                            if (newPinText.length == 4) {
                                onUpdatePin(newPinText)
                                newPinText = ""
                                Toast.makeText(context, "Şifre Başarıyla Güncellendi!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Lütfen 4 haneli bir şifre girin.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricTeal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Kaydet", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                if (profile.pinCode != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    TextButton(
                        onClick = {
                            onUpdatePin(null)
                            Toast.makeText(context, "Veli şifresi kaldırıldı.", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = HotPink)
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Unlock")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Veli Şifresini Tamamen Kaldır")
                    }
                }
            }
        }

        // Profiles Switcher List
        Text(
            text = "Kullanıcı Değiştir",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(profiles) { other ->
                if (other.id != profile.id) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSwitchProfile(other) },
                        colors = CardDefaults.cardColors(containerColor = SlateCard.copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(ElectricTeal.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = other.name.take(1).uppercase(),
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = other.name,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Main Logout Profile change button
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = SlateCard),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text("Profiller Giriş Ekranına Dön", color = Color.White)
        }
    }
}

@Composable
fun BottomBarNavigation(
    activeTab: Int,
    onTabSelect: (Int) -> Unit
) {
    NavigationBar(
        containerColor = PureVoid,
        tonalElevation = 8.dp,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        val tabItems = listOf(
            Pair("Ana Ekran", Icons.Default.Home),
            Pair("Arama", Icons.Default.Search),
            Pair("Kütüphanem", Icons.Default.FolderOpen),
            Pair("İndirilenler", Icons.Default.DownloadForOffline),
            Pair("Ayarlar", Icons.Default.ManageAccounts)
        )

        tabItems.forEachIndexed { index, pair ->
            val active = activeTab == index
            NavigationBarItem(
                selected = active,
                onClick = { onTabSelect(index) },
                icon = {
                    Icon(
                        imageVector = pair.second,
                        contentDescription = pair.first,
                        tint = if (active) Color.Black else Color.White.copy(alpha = 0.6f)
                    )
                },
                label = {
                    Text(
                        text = pair.first,
                        color = if (active) ElectricTeal else Color.White.copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = ElectricTeal
                )
            )
        }
    }
}

// Extension to retrieve the full static shows list from viewmodel securely
private fun CartoonViewModel.getShowsList(): List<CartoonShow>? {
    return try {
        val fields = this::class.java.getDeclaredField("_showsList").apply { isAccessible = true }
        (fields.get(this) as MutableStateFlow<List<CartoonShow>>).value
    } catch (e: Exception) {
        // Fallback default list construction
        com.example.data.model.CartoonData.getShows()
    }
}
