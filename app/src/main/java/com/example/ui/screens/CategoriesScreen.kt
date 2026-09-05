package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskEntity
import com.example.ui.components.TaskCardItem
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
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.TertiaryFixed

@Composable
fun CategoriesScreen(
  allTasks: List<TaskEntity>,
  selectedCategory: String?,
  onSelectCategory: (String?) -> Unit,
  onOpenShowcase: () -> Unit,
  onToggleTask: (TaskEntity) -> Unit,
  onTaskClick: (TaskEntity) -> Unit,
  onAddTask: () -> Unit,
  modifier: Modifier = Modifier
) {
  val categoriesInfo = listOf(
    Triple("TKJ", Icons.Default.Computer, Primary),
    Triple("Kelompok", Icons.Default.Group, Tertiary),
    Triple("Pribadi", Icons.Default.Person, Secondary),
    Triple("Umum", Icons.Default.Folder, Color(0xFF546E7A))
  )

  val filteredTasks = if (selectedCategory == null) {
    allTasks
  } else {
    allTasks.filter { it.category.equals(selectedCategory, ignoreCase = true) }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(12.dp))
      // Design Showcase Banner
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onOpenShowcase)
          .testTag("open_showcase_banner")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Style,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
            Column {
              Text(
                text = "Komponen Kartu Tugas",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Lihat Varian 1 & 2 Design Showcase",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 12.sp
              )
            }
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.White
          )
        }
      }
    }

    // Category Grid / Selector
    item {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Kategori Tugas",
            color = OnSurface,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
          )
          if (selectedCategory != null) {
            Text(
              text = "Tampilkan Semua",
              color = Primary,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.clickable { onSelectCategory(null) }
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          categoriesInfo.forEach { (cat, icon, color) ->
            val count = allTasks.count { it.category.equals(cat, ignoreCase = true) }
            val isSelected = selectedCategory.equals(cat, ignoreCase = true)

            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) PrimaryFixed else SurfaceContainerLowest
              ),
              elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
              modifier = Modifier
                .weight(1f)
                .clickable {
                  if (isSelected) onSelectCategory(null) else onSelectCategory(cat)
                }
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = icon,
                    contentDescription = cat,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = cat,
                  color = if (isSelected) Primary else OnSurface,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "$count tugas",
                  color = OnSurfaceVariant,
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }
    }

    // Header for task list in category
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (selectedCategory == null) "Semua Tugas (${filteredTasks.size})"
          else "Tugas Kategori $selectedCategory (${filteredTasks.size})",
          color = OnSurface,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )

        IconButton(
          onClick = onAddTask,
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(SurfaceContainerHigh)
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Tambah",
            tint = Primary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    // List of tasks
    items(filteredTasks, key = { it.id }) { task ->
      TaskCardItem(
        task = task,
        onToggleComplete = { onToggleTask(task) },
        onClick = { onTaskClick(task) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}
