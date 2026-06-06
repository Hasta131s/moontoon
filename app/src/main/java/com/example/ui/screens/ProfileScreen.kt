package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    profiles: List<UserProfile>,
    onProfileSelected: (UserProfile) -> Unit,
    onCreateProfile: (String, String, Boolean, String?) -> Unit,
    onDeleteProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    var isAddingProfile by remember { mutableStateOf(false) }
    var newProfileName by remember { mutableStateOf("") }
    var isNewKidsMode by remember { mutableStateOf(false) }
    var newProfilePin by remember { mutableStateOf("") }

    // Curated pre-selected cartoon style gradient combinations
    val avatars = listOf(
        Pair("profile_blue", Brush.linearGradient(listOf(Color(0xFF0063E5), Color(0xFF00E5C9)))),
        Pair("profile_purple", Brush.linearGradient(listOf(Color(0xFF6B11FF), Color(0xFFE5007D)))),
        Pair("profile_orange", Brush.linearGradient(listOf(Color(0xFFFF5200), Color(0xFFFFB300)))),
        Pair("profile_pink", Brush.linearGradient(listOf(Color(0xFFFF007F), Color(0xFFFF8EF2)))),
        Pair("profile_green", Brush.linearGradient(listOf(Color(0xFF00B300), Color(0xFFE5FF00))))
    )
    var selectedAvatarIndex by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isAddingProfile) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "MoonToon'u Kim İzliyor?",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Lütfen profilinizi seçin veya yeni bir tane oluşturun",
                    color = AccentGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 36.dp),
                    textAlign = TextAlign.Center
                )

                // Profiles Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .weight(1f, fill = false)
                ) {
                    items(profiles) { profile ->
                        ProfileCard(
                            profile = profile,
                            avatarBrush = getAvatarBrush(profile.avatarUrl, avatars),
                            onSelect = { onProfileSelected(profile) },
                            onDelete = { onDeleteProfile(profile) }
                        )
                    }

                    // Add Profile Action Card
                    item {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SlateCard)
                                .clickable { isAddingProfile = true }
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.08f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Profil Ekle",
                                        tint = ElectricTeal,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Profil Ekle",
                                    color = ElectricTeal,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // New Profile Form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Yeni Profil Oluştur",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Avatar Color Pick Selection Row
                    Text(
                        text = "Profil Rengi Seçin",
                        color = AccentGray,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                    ) {
                        avatars.forEachIndexed { index, pair ->
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(pair.second)
                                    .clickable { selectedAvatarIndex = index }
                                    .border(
                                        width = if (selectedAvatarIndex == index) 3.dp else 0.dp,
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    // Input Form Field
                    OutlinedTextField(
                        value = newProfileName,
                        onValueChange = { newProfileName = it },
                        label = { Text("Profil İsmi", color = Color.White.copy(alpha = 0.6f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricTeal,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                    // Kids Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ChildCare,
                                contentDescription = "Kids Mode",
                                tint = KidLightBlue,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Çocuk Modu (Kids Mode)",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Yalnızca çocuklar için güvenli içerikler",
                                    color = AccentGray,
                                    fontSize = 11.sp
                                )
                            }
                        }
                        Switch(
                            checked = isNewKidsMode,
                            onCheckedChange = { isNewKidsMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = ElectricTeal,
                                checkedTrackColor = ElectricTeal.copy(alpha = 0.4f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Optional PIN Code
                    OutlinedTextField(
                        value = newProfilePin,
                        onValueChange = { if (it.length <= 4) newProfilePin = it },
                        label = { Text("Giriş Şifresi (PIN - Opsiyonel)", color = Color.White.copy(alpha = 0.6f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricTeal,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                    )

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TextButton(
                            onClick = {
                                isAddingProfile = false
                                newProfileName = ""
                                isNewKidsMode = false
                                newProfilePin = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("İptal", color = AccentGray)
                        }

                        Button(
                            onClick = {
                                if (newProfileName.isNotBlank()) {
                                    val finalPin = if (newProfilePin.isNotBlank()) newProfilePin else null
                                    onCreateProfile(
                                        newProfileName,
                                        avatars[selectedAvatarIndex].first,
                                        isNewKidsMode,
                                        finalPin
                                    )
                                    // Reset fields
                                    isAddingProfile = false
                                    newProfileName = ""
                                    isNewKidsMode = false
                                    newProfilePin = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricTeal),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Kaydet", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    profile: UserProfile,
    avatarBrush: Brush,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {
    var showDelete by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(avatarBrush)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = profile.name.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (profile.isKidsMode) {
                        Icon(
                            imageVector = Icons.Default.ChildCare,
                            contentDescription = "Kids Mode",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    if (profile.pinCode != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = profile.name,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            // Primary profiles (Ana Profil, Çocuk Modu) cannot be deleted easily
            if (profile.name != "Ana Profil" && profile.name != "Çocuk Modu") {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Sil",
                        tint = HotPink.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private fun getAvatarBrush(avatarName: String, fallbackList: List<Pair<String, Brush>>): Brush {
    val matched = fallbackList.find { it.first == avatarName }
    return matched?.second ?: fallbackList[0].second
}
