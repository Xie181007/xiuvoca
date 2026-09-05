package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskEntity
import com.example.data.TaskPriority
import com.example.ui.theme.Error
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.PriorityHigh
import com.example.ui.theme.PriorityLow
import com.example.ui.theme.PriorityMedium
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryFixed

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskCardItem(
  task: TaskEntity,
  onToggleComplete: () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val priorityColor = when (task.priority) {
    TaskPriority.HIGH -> PriorityHigh
    TaskPriority.MEDIUM -> PriorityMedium
    TaskPriority.LOW -> PriorityLow
  }

  val categoryColor = when (task.category.lowercase()) {
    "tkj" -> Primary
    "kelompok" -> Tertiary
    "pribadi" -> Secondary
    else -> Color(0xFF546E7A)
  }

  val categoryBg = when (task.category.lowercase()) {
    "tkj" -> PrimaryFixed
    "kelompok" -> TertiaryFixed
    "pribadi" -> SecondaryFixed
    else -> SurfaceContainerHigh
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("task_item_card_${task.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Tactile Circular Checkbox Button
      Box(
        modifier = Modifier
          .padding(top = 2.dp)
          .size(24.dp)
          .clip(CircleShape)
          .background(
            if (task.isCompleted) Secondary else SurfaceContainerHigh
          )
          .clickable(onClick = onToggleComplete)
          .testTag("task_checkbox_${task.id}"),
        contentAlignment = Alignment.Center
      ) {
        if (task.isCompleted) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Task Completed",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      // Task Details Column
      Column(
        modifier = Modifier
          .weight(1f)
      ) {
        // Title and Priority Dot
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = task.title,
            color = if (task.isCompleted) OnSurfaceVariant.copy(alpha = 0.6f) else OnSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier.weight(1f, fill = false)
          )

          // Priority Indicator Dot
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(priorityColor)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tags and Metadata Row
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          // Category Pill
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(categoryBg)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = task.category,
              color = categoryColor,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }

          // Date Meta
          if (task.date.isNotBlank()) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = task.date,
                color = OnSurfaceVariant,
                fontSize = 12.sp
              )
            }
          }

          // Time Meta
          if (task.time.isNotBlank()) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = if (task.isAllDay) Icons.Outlined.Timer else Icons.Outlined.Schedule,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = task.time,
                color = OnSurfaceVariant,
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }
  }
}
