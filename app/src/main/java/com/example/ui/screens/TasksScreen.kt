package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.FloatingActionButton
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
import com.example.ui.components.MetricCard
import com.example.ui.components.MicButtonHub
import com.example.ui.components.TaskCardItem
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow

@Composable
fun TasksScreen(
  totalCount: Int,
  activeCount: Int,
  completedCount: Int,
  activeTasks: List<TaskEntity>,
  completedTasks: List<TaskEntity>,
  isCompletedExpanded: Boolean,
  onToggleCompletedDrawer: () -> Unit,
  onMicClick: () -> Unit,
  onTaskClick: (TaskEntity) -> Unit,
  onToggleTaskComplete: (TaskEntity) -> Unit,
  onAddTaskClick: () -> Unit,
  onFilterClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // 1. Central Mic Hub Companion
      item {
        Spacer(modifier = Modifier.height(14.dp))
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          MicButtonHub(
            isListening = false,
            onClick = onMicClick
          )
        }
      }

      // 2. Summary Metric Card
      item {
        Spacer(modifier = Modifier.height(6.dp))
        MetricCard(
          totalCount = totalCount,
          activeCount = activeCount,
          completedCount = completedCount
        )
      }

      // 3. Active Tasks Section Header
      item {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "Tugas Aktif",
              color = OnSurface,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )
            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(SurfaceContainerHigh)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = activeCount.toString(),
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(36.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Tune,
              contentDescription = "Filter",
              tint = OnSurfaceVariant,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }

      // 4. Active Tasks List
      items(activeTasks, key = { it.id }) { task ->
        TaskCardItem(
          task = task,
          onToggleComplete = { onToggleTaskComplete(task) },
          onClick = { onTaskClick(task) }
        )
      }

      // 5. Collapsible Completed Tasks Section
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onToggleCompletedDrawer)
            .padding(horizontal = 14.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "Tugas Selesai ($completedCount)",
              color = OnSurfaceVariant,
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold
            )
          }

          Icon(
            imageVector = if (isCompletedExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isCompletedExpanded) "Tutup" else "Buka",
            tint = OnSurfaceVariant
          )
        }
      }

      // Completed Tasks List (Expandable)
      if (isCompletedExpanded) {
        items(completedTasks, key = { it.id }) { task ->
          TaskCardItem(
            task = task,
            onToggleComplete = { onToggleTaskComplete(task) },
            onClick = { onTaskClick(task) }
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(80.dp))
      }
    }

    // Floating Action Button to Add Task
    FloatingActionButton(
      onClick = onAddTaskClick,
      containerColor = PrimaryContainer,
      contentColor = Color.White,
      shape = CircleShape,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(end = 20.dp, bottom = 20.dp)
        .testTag("fab_add_task")
    ) {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Tambah Tugas",
        modifier = Modifier.size(24.dp)
      )
    }
  }
}
