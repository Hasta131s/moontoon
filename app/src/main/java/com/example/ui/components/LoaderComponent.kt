package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ElectricTeal
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SoftBlue

@Composable
fun FullscreenLoader(
    text: String = "MoonToon Yükleniyor...",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue),
        contentAlignment = Alignment.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "loader")
        
        // Rotating spinner animation
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )

        // Pulsing scale animation
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {
                // Drawing a neon circular crescent moon orbit
                val neonTeal = ElectricTeal
                val neonBlue = SoftBlue
                
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2.5f
                    val strokeWidth = 5.dp.toPx()
                    
                    // Rotating outer ring
                    rotate(rotation) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                colors = listOf(neonTeal, neonBlue, Color.Transparent, neonTeal)
                            ),
                            radius = radius,
                            style = Stroke(width = strokeWidth)
                        )
                    }

                    // Inner glowing core represented as a cosmic moon
                    drawCircle(
                        color = neonTeal.copy(alpha = 0.3f),
                        radius = radius * 0.6f
                    )
                    
                    drawCircle(
                        color = Color.White,
                        radius = radius * 0.4f
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Text(
                text = "Çizgi Diyarına Yolculuk Başlıyor",
                color = SoftBlue.copy(alpha = 0.8f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
