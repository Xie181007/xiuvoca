package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.components.XIUVOCA_LOGO_URL
import com.example.ui.components.XiuVocaRobotMic
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryFixed
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onDismiss: () -> Unit,
  onReplay: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "splash_halo")
  val haloScale by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "halo_scale"
  )

  val spinAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "spin_angle"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFF1A237E), // Deep Navy
            Color(0xFF0D3859), // Rich Slate Blue
            Color(0xFF004D40)  // Deep Teal
          )
        )
      )
      .padding(horizontal = 24.dp, vertical = 32.dp)
      .testTag("splash_screen")
  ) {
    // Top Replay Action
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.TopCenter),
      horizontalArrangement = Arrangement.End
    ) {
      Row(
        modifier = Modifier
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.12f))
          .clickable(onClick = onReplay)
          .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Replay,
          contentDescription = "Replay",
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "Replay",
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    // Center Branding Content
    Column(
      modifier = Modifier
        .align(Alignment.Center)
        .clickable(onClick = onDismiss),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // 140dp XiuVoca Emblem with Halo & Bolt Badge
      Box(
        modifier = Modifier.size(170.dp),
        contentAlignment = Alignment.Center
      ) {
        // Glowing Halo
        Box(
          modifier = Modifier
            .size(150.dp)
            .scale(haloScale)
            .clip(CircleShape)
            .background(Color(0xFF1976D2).copy(alpha = 0.28f))
        )

        // Main circular avatar
        Box(
          modifier = Modifier
            .size(136.dp)
            .clip(CircleShape)
            .background(
              Brush.linearGradient(
                colors = listOf(Color.White.copy(alpha = 0.6f), Color.White.copy(alpha = 0.15f))
              )
            )
            .padding(3.dp),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .background(PrimaryContainer),
            contentAlignment = Alignment.Center
          ) {
            AsyncImage(
              model = ImageRequest.Builder(LocalContext.current)
                .data(XIUVOCA_LOGO_URL)
                .crossfade(true)
                .build(),
              contentDescription = "XiuVoca Emblem",
              modifier = Modifier.size(120.dp),
              error = painterResource(id = R.drawable.ic_xiuvoca_logo),
              fallback = painterResource(id = R.drawable.ic_xiuvoca_logo)
            )
          }
        }

        // Lightning Bolt Badge at top-right
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 10.dp, end = 10.dp)
            .size(24.dp)
            .clip(CircleShape)
            .background(Secondary),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Title
      Text(
        text = "XiuVoca",
        color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.5).sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Version badge
      Row(
        modifier = Modifier
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.12f))
          .padding(horizontal = 10.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Box(
          modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(SecondaryFixed)
        )
        Text(
          text = "v1.0 Offline Core",
          color = Color.White.copy(alpha = 0.9f),
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Subtitle Tagline
      Text(
        text = "Offline Voice-Driven AI Task Assistant",
        color = Color.White.copy(alpha = 0.88f),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        lineHeight = 22.sp
      )

      Spacer(modifier = Modifier.height(26.dp))

      // Initializing status indicator
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(Color.White.copy(alpha = 0.1f))
          .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Sync,
          contentDescription = null,
          tint = SecondaryFixed,
          modifier = Modifier
            .size(18.dp)
            .rotate(spinAngle)
        )
        Text(
          text = "Initializing local speech engine...",
          color = Color.White,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      Text(
        text = "Ketuk layar untuk melanjutkan",
        color = Color.White.copy(alpha = 0.5f),
        fontSize = 11.sp
      )
    }

    // Bottom On-Device Privacy Guarantee Pill
    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .clip(CircleShape)
        .background(Color.Black.copy(alpha = 0.25f))
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(
        imageVector = Icons.Outlined.VerifiedUser,
        contentDescription = null,
        tint = SecondaryFixed,
        modifier = Modifier.size(17.dp)
      )
      Text(
        text = "100% On-Device Neural Processing • No Internet Required",
        color = Color.White.copy(alpha = 0.85f),
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
      )
    }
  }
}
