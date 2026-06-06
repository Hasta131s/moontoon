package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileDownloadOff
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CartoonShow
import com.example.data.model.Episode
import com.example.data.model.OfflineDownload
import com.example.ui.theme.AccentGray
import com.example.ui.theme.ElectricTeal
import com.example.ui.theme.HotPink
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SlateCard

@Composable
fun DownloadScreen(
    downloads: List<OfflineDownload>,
    curatedShows: List<CartoonShow>,
    onPlayOffline: (Episode) -> Unit,
    onDeleteDownload: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedDownloads = downloads.filter { it.isCompleted }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .padding(16.dp)
    ) {
        // Safe System Bar Padding Title
        Text(
            text = "Kütüphanem • İndirilenler",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .statusBarsPadding()
        )

        Text(
            text = "Çevrimdışı izlemeye uygun indirilen bölümler",
            color = AccentGray,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (completedDownloads.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FileDownloadOff,
                        contentDescription = "No Downloads",
                        tint = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.size(76.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Henüz indirilmiş medya bulunmuyor.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = Alignment.CenterHorizontally.toString().let { Color.White.copy(alpha = 0.8f); "Yüklü Medya Yok" }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Bölüm listesinden istediğiniz bölümün yanındaki indirme ikonuna tıklayarak çevrimdışı izlemek üzere kaydedebilirsiniz.",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(completedDownloads) { dl ->
                    // Reconstruct Episode object to play
                    val matchedShow = curatedShows.find { it.title == dl.showTitle }
                    val matchedEpisode = matchedShow?.episodes?.find { it.url == dl.episodeUrl } ?: Episode(
                        title = dl.episodeTitle,
                        url = dl.episodeUrl,
                        logoUrl = matchedShow?.episodes?.firstOrNull()?.logoUrl ?: ""
                    )

                    DownloadRowCard(
                        download = dl,
                        episode = matchedEpisode,
                        onPlay = { onPlayOffline(matchedEpisode) },
                        onDelete = { onDeleteDownload(dl.episodeUrl) }
                    )
                }
            }
        }
    }
}

@Composable
fun DownloadRowCard(
    download: OfflineDownload,
    episode: Episode,
    onPlay: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onPlay),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Image Box with active play bubble
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = episode.logoUrl,
                    contentDescription = download.episodeTitle,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f)),
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

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.OfflinePin,
                        contentDescription = "Downloaded Status",
                        tint = ElectricTeal,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = download.showTitle,
                        color = ElectricTeal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = download.episodeTitle,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Dosya Boyutu: ~143.1 MB • Tamamlandı",
                    color = AccentGray,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Delete Download record completely
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "İndirmeyi Sil",
                    tint = HotPink.copy(alpha = 0.8f)
                )
            }
        }
    }
}
