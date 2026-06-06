package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.UserProfile
import com.example.ui.theme.ElectricTeal
import com.example.ui.theme.HotPink
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SlateCard

@Composable
fun KidsLockPinDialog(
    profile: UserProfile,
    onPinDismiss: () -> Unit,
    onSubmitPin: (String) -> Boolean
) {
    var enteredPin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onPinDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightBlue),
            color = MidnightBlue
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .navigationBarsPadding()
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Close Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onPinDismiss,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SlateCard)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = Color.White
                        )
                    }
                }

                // Core Prompt Area
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(ElectricTeal.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Kilit",
                            tint = ElectricTeal,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Çocuk Kilidi Güvenliği",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Giriş yapabilmek için 4 haneli veli şifresini tuşlayın.\n(Profil şifresi yoksa varsayılan: '8888')",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Input Dots Indicators
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0 until 4) {
                            val active = enteredPin.length > i
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 2.dp,
                                        color = if (isError) HotPink else ElectricTeal,
                                        shape = CircleShape
                                    )
                                    .background(
                                        if (active) {
                                            if (isError) HotPink else ElectricTeal
                                        } else Color.Transparent
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isError) {
                        Text(
                            text = "Hatalı şifre, tekrar deneyin!",
                            color = HotPink,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Dial Pad Keyboard Grid
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val rows = listOf(
                        listOf("1", "2", "3"),
                        listOf("4", "5", "6"),
                        listOf("7", "8", "9"),
                        listOf("C", "0", "OK")
                    )

                    rows.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            row.forEach { char ->
                                PinButton(
                                    char = char,
                                    onClick = {
                                        isError = false
                                        when (char) {
                                            "C" -> {
                                                if (enteredPin.isNotEmpty()) {
                                                    enteredPin = enteredPin.dropLast(1)
                                                }
                                            }
                                            "OK" -> {
                                                if (enteredPin.length == 4) {
                                                    val success = onSubmitPin(enteredPin)
                                                    if (!success) {
                                                        isError = true
                                                        enteredPin = ""
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Lütfen 4 haneli şifreyi tamamlayın.", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                            else -> {
                                                if (enteredPin.length < 4) {
                                                    enteredPin += char
                                                    if (enteredPin.length == 4) {
                                                        // Auto trigger OK evaluation for ease
                                                        val success = onSubmitPin(enteredPin)
                                                        if (!success) {
                                                            isError = true
                                                            enteredPin = ""
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PinButton(
    char: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(76.dp)
            .clip(CircleShape)
            .background(SlateCard)
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        when (char) {
            "C" -> Icon(
                imageVector = Icons.Default.Backspace,
                contentDescription = "Sil",
                tint = Color.White
            )
            "OK" -> Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Giriş",
                tint = ElectricTeal,
                modifier = Modifier.size(32.dp)
            )
            else -> Text(
                text = char,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
