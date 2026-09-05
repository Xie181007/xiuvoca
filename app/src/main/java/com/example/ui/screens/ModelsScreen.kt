package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@Composable
fun ModelsScreen(
  onTestVoice: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(12.dp))
      // Main Engine Banner
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth().testTag("models_engine_banner")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
                  .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Memory,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(24.dp)
                )
              }
              Column {
                Text(
                  text = "Whisper & Vosk Offline Core",
                  color = Color.White,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "Vosk Tiny-ID • Active",
                  color = SecondaryFixed,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }

            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(SecondaryFixed)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Model speech-to-text neural network yang tertanam langsung di perangkat. Seluruh pengenalan suara berjalan lokal tanpa mengirim data ke server mana pun.",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 13.sp,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = onTestVoice,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = Primary
            ),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Tes Pengenalan Suara Lokal",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // Engine Performance Metrics
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Text(
            text = "Status Performa On-Device",
            color = OnSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )

          // Latency
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
              Text(text = "Latensi Transkripsi", color = OnSurface, fontSize = 13.sp)
            }
            Text(text = "~115 ms (Real-time)", color = Secondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
          HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

          // Storage Footprint
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(imageVector = Icons.Default.Storage, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
              Text(text = "Ukuran Model Lokal", color = OnSurface, fontSize = 13.sp)
            }
            Text(text = "43.2 MB (Quantized INT8)", color = OnSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Medium)
          }
          HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

          // Privacy Guarantee
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
              Text(text = "Privasi Data", color = OnSurface, fontSize = 13.sp)
            }
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Secondary, modifier = Modifier.size(14.dp))
              Text(text = "100% Offline", color = Secondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Vocabulary and Task Grammars
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Text(
            text = "Kamus Kosakata Khusus",
            color = OnSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Dioptimasi untuk istilah teknis TKJ dan pengelolaan tugas sehari-hari:",
            color = OnSurfaceVariant,
            fontSize = 12.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          val vocabTags = listOf(
            "OSPF", "MikroTik", "VLAN", "Subnetting", "UTP Cat6", "Server", "DHCP", "Besok", "Jam 8", "Prioritas Tinggi", "Kelompok"
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            vocabTags.take(4).forEach { tag ->
              Box(
                modifier = Modifier
                  .clip(CircleShape)
                  .background(SurfaceContainerLow)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(text = tag, color = Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
              }
            }
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            vocabTags.drop(4).take(4).forEach { tag ->
              Box(
                modifier = Modifier
                  .clip(CircleShape)
                  .background(SurfaceContainerLow)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(text = tag, color = OnSurface, fontSize = 11.sp, fontWeight = FontWeight.Medium)
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(30.dp))
    }
  }
}
