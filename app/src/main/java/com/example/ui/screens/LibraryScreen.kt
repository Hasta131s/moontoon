package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CartoonShow
import com.example.data.model.FavoriteShow
import com.example.data.model.WatchHistory
import com.example.ui.theme.ElectricTeal
import com.example.ui.theme.HotPink
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SlateCard

@Composable
fun LibraryScreen(
    favorites: List<FavoriteShow>,
    history: List<WatchHistory>,
    curatedShows: List<CartoonShow>,
    onShowSelect: (CartoonShow) -> Unit,
    onClearHistory: () -> Unit,
    onDeleteHistoryItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Favorites, 1: History

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .padding(16.dp)
    ) {
        // Safe System Bar Padding Title
        Text(
            text = "Kütüphanem",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .statusBarsPadding()
        )

        // Custom Top Tabs Header Swapper
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SlateCard)
                .padding(4.dp)
        ) {
            TabButton(
                title = "Favorilerim",
                icon = Icons.Default.Favorite,
                isSelected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                title = "İzleme Geçmişi",
                icon = Icons.Default.History,
                isSelected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f)
            )
        }

        // Render Tabs Content Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (selectedTab) {
                0 -> {
                    // Match favorites shows
                    val favoriteShowsList = curatedShows.filter { show ->
                        favorites.any { it.showTitle == show.title }
                    }

                    if (favoriteShowsList.isEmpty()) {
                        LibraryEmptyState(
                            message = "Henüz favorilere eklenmiş dizi bulunmuyor.",
                            sub = "Dizi detay sayfasından sevdiğiniz çizgi filmleri favorileyebilirsiniz."
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(favoriteShowsList) { show ->
                                FavoriteShowRowCard(show = show, onClick = { onShowSelect(show) })
                            }
                        }
                    }
                }
                1 -> {
                    if (history.isEmpty()) {
                        LibraryEmptyState(
                            message = "İzleme geçmişiniz henüz boş.",
                            sub = "Çizgi filmleri izlemeye başladığınızda geçmişiniz burada yer alacaktır."
                        )
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Clear All Button top right
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = onClearHistory,
                                    colors = ButtonDefaults.textButtonColors(contentColor = HotPink)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteSweep,
                                        contentDescription = "Temizle"
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Geçmişi Temizle", fontWeight = FontWeight.Bold)
                                }
                            }

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(history) { record ->
                                    HistoryEpisodeRowCard(
                                        record = record,
                                        onPlay = {
                                            val matched = curatedShows.find { it.title == record.showTitle }
                                            if (matched != null) onShowSelect(matched)
                                        },
                                        onDelete = { onDeleteHistoryItem(record.episodeUrl) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) ElectricTeal else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) Color.Black else Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                color = if (isSelected) Color.Black else Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LibraryEmptyState(message: String, sub: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "Empty",
                tint = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.size(76.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = sub,
                color = Color.White.copy(alpha = 0.4e-01f), // using visible white alpha instead
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun FavoriteShowRowCard(
    show: CartoonShow,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageSample = show.episodes.firstOrNull()?.logoUrl
            if (imageSample != null) {
                AsyncImage(
                    model = imageSample,
                    contentDescription = show.title,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = show.title,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = show.genres.joinToString(" "),
                    color = ElectricTeal,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${show.episodes.size} adet bölüm hazır",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Oynat",
                tint = ElectricTeal,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun HistoryEpisodeRowCard(
    record: WatchHistory,
    onPlay: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Episode Cover Badge
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onPlay)
            ) {
                AsyncImage(
                    model = record.logoUrl,
                    contentDescription = record.episodeTitle,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.showTitle,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = record.episodeTitle,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Active timeline progress slider simulation
                val frac = if (record.totalDurationMs > 0) {
                    record.progressMs.toFloat() / record.totalDurationMs.toFloat()
                } else 0.5f
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { frac },
                        color = ElectricTeal,
                        trackColor = Color.White.copy(alpha = 0.1f),
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "İzleme: %${(frac * 100).toInt()}",
                        color = AccentGray,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Delete single record button
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Sil",
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
