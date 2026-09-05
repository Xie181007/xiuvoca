package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SecondaryFixed

const val XIUVOCA_LOGO_URL =
  "https://lh3.googleusercontent.com/aida/AEtjO1U-DjYOxjzZSnwukXd8DWKbB7YzsvrRpGVZuaEvlTdjrDr50hDgsJisvKZ0M5zexu9vjkux1dvlByEDb3zXmB-woHxN6NJBMP19mdWl_dVAD4-j9TcehqhYO0oD4aJQjoUad-uBa6HQmYFTvWR-oW_0bTkkekEhetgmhEoJAwwGr0CUNEg18e2iIzo-Ft8R66UVCeYInnK6di8IwFp7eNbQoybGGVNSu3ZVOPie3XECTYax7a2M92QGxs0"

@Composable
fun XiuVocaTopBar(
  onMoreOptionsClick: () -> Unit,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(PrimaryContainer)
      .height(56.dp)
      .padding(horizontal = 16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().height(56.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Left: Logo & App Title & Offline Badge
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Logo
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(XIUVOCA_LOGO_URL)
              .crossfade(true)
              .build(),
            contentDescription = "XiuVoca Logo",
            modifier = Modifier.size(32.dp),
            error = painterResource(id = R.drawable.ic_xiuvoca_logo),
            fallback = painterResource(id = R.drawable.ic_xiuvoca_logo)
          )
        }

        Text(
          text = "XiuVoca",
          color = Color.White,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = (-0.3).sp
        )

        // Offline badge pill
        Row(
          modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xFF046B5E).copy(alpha = 0.35f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(SecondaryFixed)
          )
          Text(
            text = "Offline",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      // Right: Actions (More options and profile)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        IconButton(
          onClick = onMoreOptionsClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("topbar_more_options_button")
        ) {
          Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Options",
            tint = Color.White
          )
        }

        IconButton(
          onClick = onProfileClick,
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Primary)
            .testTag("topbar_profile_button")
        ) {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Profile",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
