package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskPriority
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.PriorityHigh
import com.example.ui.theme.PriorityLow
import com.example.ui.theme.PriorityMedium
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryFixed

data class ShowcaseTaskItem(
  val id: String,
  val title: String,
  val category: String,
  val metaTime: String,
  val priority: TaskPriority,
  var isCompleted: Boolean = false,
  val completedAtText: String? = null
)

@Composable
fun CardShowcaseScreen(
  onBack: () -> Unit,
  onVoiceTrigger: () -> Unit,
  modifier: Modifier = Modifier
) {
  val activeItems = remember {
    mutableStateListOf(
      ShowcaseTaskItem("1", "Instalasi server OSPF", "TKJ", "05 Sep 2026 • 08:00", TaskPriority.HIGH),
      ShowcaseTaskItem("2", "Kumpul laporan kelompok 5", "Kelompok", "11 Sep 2026 • Sepanjang hari", TaskPriority.MEDIUM),
      ShowcaseTaskItem("3", "Olahraga pagi", "Pribadi", "Besok, 06:30", TaskPriority.LOW)
    )
  }

  val completedItems = remember {
    mutableStateListOf(
      ShowcaseTaskItem("4", "Presentasi jaringan komputer", "TKJ", "", TaskPriority.HIGH, isCompleted = true, completedAtText = "Selesai 04 Sep 2026, 16:45"),
      ShowcaseTaskItem("5", "Konfigurasi DHCP MikroTik", "TKJ", "", TaskPriority.HIGH, isCompleted = true, completedAtText = "Selesai 03 Sep 2026, 14:10")
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      // Header Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          IconButton(onClick = onBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Kembali",
              tint = OnSurface
            )
          }
          Column {
            Text(
              text = "Komponen Kartu Tugas",
              color = OnSurface,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Design Showcase · Offline Core",
              color = OnSurfaceVariant,
              fontSize = 12.sp
            )
          }
        }

        // Filter chip button
        Row(
          modifier = Modifier
            .clip(CircleShape)
            .background(SurfaceContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(16.dp)
          )
          Text(text = "Filter", color = Primary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
      }
    }

    // Voice Command Prompt Banner
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onVoiceTrigger)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(22.dp)
            )
          }
          Column(modifier = Modifier.weight(1f)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(
                text = "Ucapkan perintah suara",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
              )
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(SecondaryFixed)
              )
            }
            Text(
              text = "Contoh: \"Selesaikan tugas OSPF\" atau \"Tandai presentasi selesai\"",
              color = Color.White.copy(alpha = 0.88f),
              fontSize = 12.sp,
              modifier = Modifier.padding(top = 2.dp)
            )
          }
        }
      }
    }

    // Varian 1 — Tugas Aktif
    item {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Primary))
            Text(
              text = "Varian 1 — Tugas Aktif",
              color = OnSurface,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(SurfaceContainerHigh)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${activeItems.size} tugas",
              color = Primary,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
        Text(
          text = "Ketuk checkbox untuk menyelesaikan tugas",
          color = OnSurfaceVariant,
          fontSize = 12.sp,
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }

    items(activeItems, key = { it.id }) { item ->
      ShowcaseActiveCard(
        item = item,
        onToggle = {
          activeItems.remove(item)
          completedItems.add(
            item.copy(
              isCompleted = true,
              completedAtText = "Baru saja diselesaikan"
            )
          )
        }
      )
    }

    // Varian 2 — Tugas Selesai
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Secondary))
            Text(
              text = "Varian 2 — Tugas Selesai",
              color = OnSurface,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(SecondaryContainer)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${completedItems.size} tugas",
              color = Secondary,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
        Text(
          text = "Status tercatat secara offline · Klik centang untuk memulihkan",
          color = OnSurfaceVariant,
          fontSize = 12.sp,
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }

    items(completedItems, key = { it.id }) { item ->
      ShowcaseCompletedCard(
        item = item,
        onRestore = {
          completedItems.remove(item)
          activeItems.add(
            item.copy(
              isCompleted = false,
              metaTime = "Baru dipulihkan"
            )
          )
        }
      )
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun ShowcaseActiveCard(
  item: ShowcaseTaskItem,
  onToggle: () -> Unit
) {
  val stripColor = when (item.priority) {
    TaskPriority.HIGH -> PriorityHigh
    TaskPriority.MEDIUM -> PriorityMedium
    TaskPriority.LOW -> PriorityLow
  }

  val categoryColor = when (item.category) {
    "TKJ" -> Primary
    "Kelompok" -> Tertiary
    else -> Secondary
  }
  val categoryBg = when (item.category) {
    "TKJ" -> PrimaryFixed
    "Kelompok" -> TertiaryFixed
    else -> SecondaryFixed
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Circular Checkbox
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(SurfaceContainerHigh)
          .clickable(onClick = onToggle)
      )

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = item.title,
            color = OnSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(stripColor))
            Text(
              text = item.priority.label,
              color = stripColor,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(categoryBg)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(text = item.category, color = categoryColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
          Text(text = item.metaTime, color = OnSurfaceVariant, fontSize = 12.sp)
        }
      }
    }
  }
}

@Composable
private fun ShowcaseCompletedCard(
  item: ShowcaseTaskItem,
  onRestore: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest.copy(alpha = 0.85f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(Primary)
          .clickable(onClick = onRestore),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Pulihkan",
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = item.title,
            color = Outline,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.LineThrough
          )
          Icon(
            imageVector = Icons.Outlined.TaskAlt,
            contentDescription = null,
            tint = Secondary,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(SurfaceContainerHigh)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(text = "${item.category} Selesai", color = OnSurfaceVariant, fontSize = 11.sp)
          }
          if (item.completedAtText != null) {
            Text(text = item.completedAtText, color = Outline, fontSize = 12.sp)
          }
        }
      }
    }
  }
}
