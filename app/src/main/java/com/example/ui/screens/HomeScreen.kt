package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CartoonShow
import com.example.data.model.UserProfile
import com.example.data.model.WatchHistory
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    shows: List<List<CartoonShow>>, // grouped or simple list
    curatedShows: List<CartoonShow>,
    activeProfile: UserProfile?,
    recentWatchList: List<WatchHistory>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onShowSelected: (CartoonShow) -> Unit,
    onNotificationTrigger: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("Hepsi", "#komedi", "#macera", "#aksiyon", "#fantastik", "#yerli", "#dostluk")

    // Random selected Highlight Hero Show (e.g. Adventure Time)
    val heroShow = curatedShows.firstOrNull() ?: CartoonShow(
        "MoonToon", "Adventure", emptyList(), "Mükemmel dünyayı keşfe çıkın!", rating = "8.2", episodes = emptyList()
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
    ) {
        // App Custom Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "MoonToon",
                    color = ElectricTeal,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "${activeProfile?.name ?: "Ziyaretçi"} İçin Seçilenler",
                    color = AccentGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Fun Notification Trigger Notification
            IconButton(
                onClick = onNotificationTrigger,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SlateCard)
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = "Test Notification",
                    tint = ElectricTeal,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Home Vertical Scrollable Content
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            // Hero Spotlight Widescreen banner (Spans full columns)
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                HeroBanner(show = heroShow, onPlayClick = { onShowSelected(heroShow) })
            }

            // Continue Watching Title & Row (Spans full columns)
            if (recentWatchList.isNotEmpty()) {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(
                            text = "İzlemeye Devam Et",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(recentWatchList) { history ->
                                ContinueWatchingCard(history = history, onSelect = {
                                    // Custom rebuild selected show for history stream play
                                    val matched = curatedShows.find { it.title == history.showTitle }
                                    if (matched != null) {
                                        onShowSelected(matched)
                                    }
                                })
                            }
                        }
                    }
                }
            }

            // Category Chips Selection Row (Spans full columns)
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                    Text(
                        text = "Kategoriler",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            CategoryChip(
                                category = category,
                                isSelected = selectedCategory == category,
                                onClick = { onCategorySelected(category) }
                            )
                        }
                    }
                }
            }

            // Bottom Grid Content Title
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Text(
                    text = if (selectedCategory == "Hepsi") "Önerilen Çizgi Diziler" else "$selectedCategory Tarzı Diziler",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }

            // Grid Show Cards
            items(curatedShows) { show ->
                ShowItemGridCard(show = show, onClick = { onShowSelected(show) })
            }

            // Grid Pad Safe Margin
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun HeroBanner(
    show: CartoonShow,
    onPlayClick: () -> Unit
) {
    // Beautiful stylized card representation with overlay gradient
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(onClick = onPlayClick),
        shape = RoundedCornerShape(16.dp),
        border = borderStrokeGlow(),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Simulated widescreen illustration using a canvas/gradient backup and Coil AsyncImage
            val backgroundGradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
            )

            val showPosterMock = show.episodes.firstOrNull()?.logoUrl
            if (showPosterMock != null) {
                AsyncImage(
                    model = showPosterMock,
                    contentDescription = show.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.65f
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundGradient)
            )

            // Content details info overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(HotPink)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "GÜNÜN SEÇİMİ",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "IMDb",
                            tint = GoldRating,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = " ${show.rating}",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = show.title,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = show.description,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal
                )
            }

            // Quick Play Button Overlay icon
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(ElectricTeal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Hemen İzle",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ContinueWatchingCard(
    history: WatchHistory,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(110.dp)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Video Thumbnail AsyncImage
            AsyncImage(
                model = history.logoUrl,
                contentDescription = history.episodeTitle,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )

            // Dark Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
            )

            // Play icon indicator bubble
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Resume",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )

            // Content details info overlay bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = history.showTitle,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = history.episodeTitle,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Progress Bar simulation Indicator bottom
            val progressFrac = if (history.totalDurationMs > 0) {
                history.progressMs.toFloat() / history.totalDurationMs.toFloat()
            } else 0.5f

            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFrac.coerceIn(0.1f, 1f))
                    .height(4.dp)
                    .align(Alignment.BottomStart)
                    .background(ElectricTeal)
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) ElectricTeal else SlateCard,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun ShowItemGridCard(
    show: CartoonShow,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Thumbnail image background
            val showPosterBase = show.episodes.lastOrNull()?.logoUrl
            if (showPosterBase != null) {
                AsyncImage(
                    model = showPosterBase,
                    contentDescription = show.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.8f
                )
            }

            // Dark Bottom vignette gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.95f))
                        )
                    )
            )

            // Info details overlay bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = show.title,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = show.genres.take(2).joinToString(" "),
                        color = ElectricTeal,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = GoldRating,
                            modifier = Modifier.size(10.dp)
                        )
                        Text(
                            text = " ${show.rating}",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun borderStrokeGlow(): BorderStroke {
    return androidx.compose.foundation.BorderStroke(
        width = 1.dp,
        color = ElectricTeal.copy(alpha = 0.3f)
    )
}
