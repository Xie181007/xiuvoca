package com.example.ui.components

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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.ui.theme.Error
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Secondary
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest

@Composable
fun OverflowDropdown(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  onActionClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  if (expanded) {
    Popup(
      alignment = Alignment.TopEnd,
      onDismissRequest = onDismissRequest,
      properties = PopupProperties(focusable = true)
    ) {
      Column(
        modifier = modifier
          .padding(top = 48.dp, end = 12.dp)
          .width(230.dp),
        horizontalAlignment = Alignment.End
      ) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
          modifier = Modifier.fillMaxWidth().testTag("overflow_popup_card")
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            OverflowMenuItem(
              icon = Icons.Default.Settings,
              label = "Pengaturan",
              onClick = {
                onActionClick("Pengaturan")
                onDismissRequest()
              }
            )
            HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

            OverflowMenuItem(
              icon = Icons.Default.Info,
              label = "Tentang",
              onClick = {
                onActionClick("Tentang")
                onDismissRequest()
              }
            )
            HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

            OverflowMenuItem(
              icon = Icons.Default.HelpOutline,
              label = "Bantuan",
              onClick = {
                onActionClick("Bantuan")
                onDismissRequest()
              }
            )
            HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

            OverflowMenuItem(
              icon = Icons.Default.Star,
              label = "Beri Rating",
              onClick = {
                onActionClick("Beri Rating")
                onDismissRequest()
              }
            )
            HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

            OverflowMenuItem(
              icon = Icons.Default.Share,
              label = "Bagikan",
              onClick = {
                onActionClick("Bagikan")
                onDismissRequest()
              }
            )
            HorizontalDivider(color = SurfaceContainerHigh, thickness = 0.8.dp)

            OverflowMenuItem(
              icon = Icons.AutoMirrored.Filled.ExitToApp,
              label = "Keluar",
              isDestructive = true,
              onClick = {
                onActionClick("Keluar")
                onDismissRequest()
              }
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Badge: Vosk Tiny-ID • Ready
        Row(
          modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xFFD1ECFA).copy(alpha = 0.95f))
            .padding(horizontal = 12.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(Secondary)
          )
          Text(
            text = "Vosk Tiny-ID • Ready",
            color = OnSurface,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
private fun OverflowMenuItem(
  icon: ImageVector,
  label: String,
  isDestructive: Boolean = false,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(48.dp)
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isDestructive) Error else OnSurfaceVariant,
      modifier = Modifier.size(20.dp)
    )
    Text(
      text = label,
      color = if (isDestructive) Error else OnSurface,
      fontSize = 14.sp,
      fontWeight = if (isDestructive) FontWeight.SemiBold else FontWeight.Medium
    )
  }
}
