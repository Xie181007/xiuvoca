package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.PrimaryFixedDim
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceListeningSheet(
  isVisible: Boolean,
  transcriptText: String,
  onDismiss: () -> Unit,
  onFinish: () -> Unit
) {
  if (!isVisible) return

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val infiniteTransition = rememberInfiniteTransition(label = "voice_ripples")

  val ripple1Scale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.35f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "r1_scale"
  )
  val ripple1Alpha by infiniteTransition.animateFloat(
    initialValue = 0.45f,
    targetValue = 0.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "r1_alpha"
  )

  val ripple2Scale by infiniteTransition.animateFloat(
    initialValue = 0.98f,
    targetValue = 1.2f,
    animationSpec = infiniteRepeatable(
      animation = tween(1600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "r2_scale"
  )

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = SurfaceContainerLowest,
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(top = 12.dp, bottom = 8.dp)
          .size(width = 36.dp, height = 4.dp)
          .clip(CircleShape)
          .background(OutlineVariant)
      )
    },
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .testTag("voice_listening_sheet"),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Active Mode Pill
      Row(
        modifier = Modifier
          .clip(CircleShape)
          .background(SurfaceContainerHigh)
          .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(Primary)
        )
        Text(
          text = "SESI SUARA AKTIF",
          color = Primary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Concentric Ripple Waves + Centered 120dp Mic Button
      Box(
        modifier = Modifier.size(170.dp),
        contentAlignment = Alignment.Center
      ) {
        // Outer Ripple
        Box(
          modifier = Modifier
            .size(150.dp)
            .scale(ripple1Scale)
            .clip(CircleShape)
            .background(PrimaryFixedDim.copy(alpha = ripple1Alpha))
        )

        // Mid Ripple
        Box(
          modifier = Modifier
            .size(136.dp)
            .scale(ripple2Scale)
            .clip(CircleShape)
            .background(PrimaryFixed.copy(alpha = 0.45f))
        )

        // Center 120dp Mic Button
        Box(
          modifier = Modifier
            .size(118.dp)
            .shadow(
              elevation = 16.dp,
              shape = CircleShape,
              ambientColor = Color(0xFF1976D2).copy(alpha = 0.4f),
              spotColor = Color(0xFF1976D2).copy(alpha = 0.5f)
            )
            .clip(CircleShape)
            .background(
              Brush.verticalGradient(
                colors = listOf(Primary, PrimaryContainer)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          XiuVocaRobotMic(modifier = Modifier.size(68.dp))
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Live Voice Waveform Bars Visualizer
      WaveformVisualizer(modifier = Modifier.height(26.dp))

      Spacer(modifier = Modifier.height(8.dp))

      // Status Indicator
      Text(
        text = "Mendengarkan...",
        color = Primary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )

      // Offline Engine Guarantee
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Outlined.Verified,
          contentDescription = null,
          tint = Secondary,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "Whisper Offline AI Engine aktif • Tanpa Internet",
          color = OnSurfaceVariant,
          fontSize = 12.sp
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Real-Time Speech Transcript Card
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.GraphicEq,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = "TRANSKRIPSI LANGSUNG",
                color = Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
            }

            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(SurfaceContainerHigh)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = "Indonesia",
                color = OnSurfaceVariant,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Speech text
          Text(
            text = if (transcriptText.isNotBlank()) "“$transcriptText”"
            else "“Besok jam 8 pagi instalasi server OSPF, terus hari Jumat depan kumpul laporan kelompok 5”",
            color = OnSurface,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            lineHeight = 20.sp,
            modifier = Modifier.testTag("live_transcript_text")
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Detected Intent Tags Micro-preview
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(SurfaceContainerLowest.copy(alpha = 0.7f))
              .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              modifier = Modifier
                .clip(CircleShape)
                .background(PrimaryFixed.copy(alpha = 0.7f))
                .padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = "Besok 08:00 WIB",
                color = Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }

            Row(
              modifier = Modifier
                .clip(CircleShape)
                .background(SecondaryFixed.copy(alpha = 0.6f))
                .padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.Folder,
                contentDescription = null,
                tint = Secondary,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = "TKJ & Kelompok",
                color = Secondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Bottom Action Buttons: Batal & Selesai
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = SurfaceContainer,
            contentColor = OnSurfaceVariant
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("voice_cancel_button")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Batal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(
          onClick = onFinish,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("voice_finish_button")
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Selesai", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun WaveformVisualizer(modifier: Modifier = Modifier) {
  val infiniteTransition = rememberInfiniteTransition(label = "waveform_anim")

  val h1 by infiniteTransition.animateFloat(
    initialValue = 8f,
    targetValue = 24f,
    animationSpec = infiniteRepeatable(
      animation = tween(450, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h1"
  )
  val h2 by infiniteTransition.animateFloat(
    initialValue = 18f,
    targetValue = 10f,
    animationSpec = infiniteRepeatable(
      animation = tween(520, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h2"
  )
  val h3 by infiniteTransition.animateFloat(
    initialValue = 12f,
    targetValue = 26f,
    animationSpec = infiniteRepeatable(
      animation = tween(400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h3"
  )
  val h4 by infiniteTransition.animateFloat(
    initialValue = 20f,
    targetValue = 8f,
    animationSpec = infiniteRepeatable(
      animation = tween(480, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h4"
  )
  val h5 by infiniteTransition.animateFloat(
    initialValue = 10f,
    targetValue = 22f,
    animationSpec = infiniteRepeatable(
      animation = tween(430, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h5"
  )
  val h6 by infiniteTransition.animateFloat(
    initialValue = 16f,
    targetValue = 6f,
    animationSpec = infiniteRepeatable(
      animation = tween(560, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "h6"
  )

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    listOf(h1, h2, h3, h4, h5, h6).forEach { height ->
      Box(
        modifier = Modifier
          .width(4.dp)
          .height(height.dp)
          .clip(CircleShape)
          .background(Primary)
      )
    }
  }
}
