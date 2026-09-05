package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixedDim
import com.example.ui.theme.SecondaryFixed

@Composable
fun MicButtonHub(
  isListening: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_halo")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = 1.18f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )

  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.45f,
    targetValue = 0.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_alpha"
  )

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.size(144.dp)
    ) {
      // Ambient Glow Ring
      Box(
        modifier = Modifier
          .size(136.dp)
          .scale(if (isListening) pulseScale * 1.08f else pulseScale)
          .clip(CircleShape)
          .background(
            if (isListening) SecondaryFixed.copy(alpha = pulseAlpha + 0.1f)
            else PrimaryFixedDim.copy(alpha = pulseAlpha)
          )
      )

      // Main 120dp Tactile Mic Button
      Box(
        modifier = Modifier
          .size(120.dp)
          .shadow(
            elevation = 12.dp,
            shape = CircleShape,
            ambientColor = Color(0xFF1976D2).copy(alpha = 0.4f),
            spotColor = Color(0xFF1976D2).copy(alpha = 0.5f)
          )
          .clip(CircleShape)
          .background(
            Brush.verticalGradient(
              colors = listOf(PrimaryContainer, Primary)
            )
          )
          .clickable(onClick = onClick)
          .testTag("main_voice_mic_button"),
        contentAlignment = Alignment.Center
      ) {
        // Cute XiuVoca Robot Mic Character Canvas
        XiuVocaRobotMic(modifier = Modifier.size(68.dp))
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = if (isListening) "Mendengarkan suara..." else "Ketuk untuk berbicara",
      color = if (isListening) Primary else Outline,
      fontSize = 14.sp,
      fontWeight = if (isListening) FontWeight.SemiBold else FontWeight.Medium,
      modifier = Modifier.testTag("voice_status_label")
    )
  }
}

@Composable
fun XiuVocaRobotMic(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val scale = w / 72f

    // 1. Neural Nodes (4 circles & curved paths)
    // Top-left
    drawCircle(
      color = Color.White,
      radius = 2.5f * scale,
      center = Offset(12f * scale, 26f * scale)
    )
    drawPath(
      path = Path().apply {
        moveTo(14f * scale, 26f * scale)
        cubicTo(18f * scale, 26f * scale, 21f * scale, 29f * scale, 22f * scale, 34f * scale)
      },
      color = Color.White.copy(alpha = 0.85f),
      style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round)
    )

    // Bottom-left
    drawCircle(
      color = Color.White,
      radius = 2.5f * scale,
      center = Offset(12f * scale, 46f * scale)
    )
    drawPath(
      path = Path().apply {
        moveTo(14f * scale, 46f * scale)
        cubicTo(18f * scale, 46f * scale, 22f * scale, 43f * scale, 23f * scale, 38f * scale)
      },
      color = Color.White.copy(alpha = 0.85f),
      style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round)
    )

    // Top-right
    drawCircle(
      color = Color.White,
      radius = 2.5f * scale,
      center = Offset(60f * scale, 26f * scale)
    )
    drawPath(
      path = Path().apply {
        moveTo(58f * scale, 26f * scale)
        cubicTo(54f * scale, 26f * scale, 51f * scale, 29f * scale, 50f * scale, 34f * scale)
      },
      color = Color.White.copy(alpha = 0.85f),
      style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round)
    )

    // Bottom-right
    drawCircle(
      color = Color.White,
      radius = 2.5f * scale,
      center = Offset(60f * scale, 46f * scale)
    )
    drawPath(
      path = Path().apply {
        moveTo(58f * scale, 46f * scale)
        cubicTo(54f * scale, 46f * scale, 50f * scale, 43f * scale, 49f * scale, 38f * scale)
      },
      color = Color.White.copy(alpha = 0.85f),
      style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round)
    )

    // 2. Mic Stand Base & Cradle
    drawPath(
      path = Path().apply {
        moveTo(26f * scale, 33f * scale)
        cubicTo(26f * scale, 40f * scale, 30.5f * scale, 45f * scale, 36f * scale, 45f * scale)
        cubicTo(41.5f * scale, 45f * scale, 46f * scale, 40f * scale, 46f * scale, 33f * scale)
      },
      color = Color.White,
      style = Stroke(width = 3.2f * scale, cap = StrokeCap.Round)
    )
    // Stem
    drawLine(
      color = Color.White,
      start = Offset(36f * scale, 45f * scale),
      end = Offset(36f * scale, 54f * scale),
      strokeWidth = 3.2f * scale,
      cap = StrokeCap.Round
    )
    // Base
    drawLine(
      color = Color.White,
      start = Offset(28f * scale, 54f * scale),
      end = Offset(44f * scale, 54f * scale),
      strokeWidth = 3.2f * scale,
      cap = StrokeCap.Round
    )

    // 3. Robot Capsule Head
    drawRoundRect(
      color = Color.White,
      topLeft = Offset(29f * scale, 19f * scale),
      size = Size(14f * scale, 22f * scale),
      cornerRadius = CornerRadius(7f * scale, 7f * scale)
    )

    // 4. Robot Eyes (Deep blue)
    val eyeColor = Color(0xFF005DAC)
    drawCircle(
      color = eyeColor,
      radius = 1.4f * scale,
      center = Offset(33f * scale, 26f * scale)
    )
    drawCircle(
      color = eyeColor,
      radius = 1.4f * scale,
      center = Offset(39f * scale, 26f * scale)
    )

    // 5. Friendly Smile
    drawPath(
      path = Path().apply {
        moveTo(34.5f * scale, 32f * scale)
        quadraticTo(36f * scale, 33.3f * scale, 37.5f * scale, 32f * scale)
      },
      color = eyeColor,
      style = Stroke(width = 1.5f * scale, cap = StrokeCap.Round)
    )
  }
}
