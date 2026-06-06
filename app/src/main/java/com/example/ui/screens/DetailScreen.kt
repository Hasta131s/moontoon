package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.*
import com.example.ui.components.FullscreenLoader
import com.example.ui.theme.*

@Composable
fun DetailScreen(
    show: CartoonShow,
    omdbDetails: OmdbResponse?,
    isLoadingOmdb: Boolean,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    downloads: List<OfflineDownload>,
    onToggleDownload: (Episode) -> Unit,
    onPlayEpisode: (Episode) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamically query unique seasons available in parsed local list
    val uniqueSeasons = show.episodes.map { it.season }.distinct().sorted()
    var selectedSeason by remember { mutableStateOf(uniqueSeasons.firstOrNull() ?: 1) }

    // Group episodes by season
    val episodesInSeason = show.episodes.filter { it.season == selectedSeason }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
    ) {
        // Scrollable content body
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Image Backdrop
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    val backdropMock = show.episodes.firstOrNull()?.logoUrl
                    if (backdropMock != null) {
                        AsyncImage(
                            model = backdropMock,
                            contentDescription = "Backdrop",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MidnightBlue.copy(alpha = 0.5f),
                                        MidnightBlue
                                    )
                                )
                            )
                    )

                    // Overlay Title on bottom of backdrop
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = show.title,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 38.sp
                        )

                        Text(
                            text = show.genres.joinToString(" "),
                            color = ElectricTeal,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Quick Play & Save Control Action Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play first episode button
                    Button(
                        onClick = {
                            val first = show.episodes.firstOrNull()
                            if (first != null) onPlayEpisode(first)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricTeal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1.5f),
                        contentPadding = PaddingValues(vertical = 14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Oynat",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hemen İzle",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    // Save / Favorite Action Button
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SlateCard)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favori",
                            tint = if (isFavorite) HotPink else Color.White
                        )
                    }
                }
            }

            // OMDB Metadata description and credits
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    if (isLoadingOmdb) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = ElectricTeal)
                        }
                    } else if (omdbDetails != null) {
                        // Display stats
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            RatingBadge("IMDb ${omdbDetails.imdbRating ?: show.rating}")
                            TextInfo(omdbDetails.Year ?: show.years)
                            TextInfo("Sezon: ${omdbDetails.totalSeasons ?: show.seasonsCount}")
                        }

                        // Plot Summary
                        Text(
                            text = "Özet",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        Text(
                            text = omdbDetails.Plot ?: show.description,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Credits Info
                        if (omdbDetails.Actors != null) {
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text("Oyuncular: ", color = ElectricTeal, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(omdbDetails.Actors, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            }
                        }
                        if (omdbDetails.Genre != null) {
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text("Tür: ", color = ElectricTeal, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(omdbDetails.Genre, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            }
                        }
                    } else {
                        // Fallback local info if network offline
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            RatingBadge("IMDb ${show.rating}")
                            TextInfo(show.years)
                            TextInfo("Sezon: ${show.seasonsCount}")
                        }

                        Text(
                            text = show.description,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Divider separator
            item {
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.08f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)
                )
            }

            // Expandable Season Selector header tabs
            if (uniqueSeasons.size > 1) {
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
                        Text(
                            text = "Sezon Seçin",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(uniqueSeasons) { seasonNum ->
                                val active = seasonNum == selectedSeason
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable { selectedSeason = seasonNum },
                                    color = if (active) ElectricTeal else SlateCard,
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = "$seasonNum. Sezon",
                                        color = if (active) Color.Black else Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Episode List Card Rows
            item {
                Text(
                    text = "Bölümler (${episodesInSeason.size} adet)",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 12.dp)
                )
            }

            items(episodesInSeason) { ep ->
                val matchingDownload = downloads.find { it.episodeUrl == ep.url }
                EpisodeRowItem(
                    episode = ep,
                    download = matchingDownload,
                    onPlay = { onPlayEpisode(ep) },
                    onDownload = { onToggleDownload(ep) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))
            }
        }

        // Floating Back Button top left
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
                .clip(CircleShape)
                .background(MidnightBlue.copy(alpha = 0.8f))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Geri",
                tint = Color.White
            )
        }
    }
}

@Composable
fun EpisodeRowItem(
    episode: Episode,
    download: OfflineDownload?,
    onPlay: () -> Unit,
    onDownload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Episode Image Box
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onPlay)
            ) {
                AsyncImage(
                    model = episode.logoUrl,
                    contentDescription = episode.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Oynat",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Middle Episode Descriptive Text Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bölüm ${episode.number}",
                    color = ElectricTeal,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = episode.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "İzleme Süresi: ~11 dakika (HLS Akış)",
                    color = AccentGray,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Right Download System Control
            IconButton(
                onClick = onDownload,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f))
            ) {
                if (download == null) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = Color.White
                    )
                } else if (!download.isCompleted) {
                    // Downloading spinner progress
                    val prog = if (download.bytesTotal > 0) {
                        (download.bytesDownloaded.toFloat() / download.bytesTotal.toFloat())
                    } else 0f
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { prog },
                            color = ElectricTeal,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(32.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel",
                            tint = HotPink,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                } else {
                    // Completed status
                    Icon(
                        imageVector = Icons.Default.OfflinePin,
                        contentDescription = "Çevrimdışı İndirildi",
                        tint = ElectricTeal
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBadge(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(GoldRating.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = GoldRating,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TextInfo(text: String) {
    Text(
        text = text,
        color = AccentGray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
}
