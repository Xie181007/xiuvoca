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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskEntity
import com.example.data.TaskPriority
import com.example.ui.theme.Error
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
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryFixed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskReviewSheet(
  isVisible: Boolean,
  initialTask: TaskEntity?,
  onDismiss: () -> Unit,
  onSave: (
    title: String,
    category: String,
    date: String,
    time: String,
    isAllDay: Boolean,
    priority: TaskPriority,
    transcript: String?
  ) -> Unit
) {
  if (!isVisible) return

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var title by remember(initialTask) { mutableStateOf(initialTask?.title ?: "Instalasi server OSPF") }
  var category by remember(initialTask) { mutableStateOf(initialTask?.category ?: "TKJ") }
  var date by remember(initialTask) { mutableStateOf(initialTask?.date ?: "05 Sep 2026") }
  var time by remember(initialTask) { mutableStateOf(initialTask?.time ?: "08:00 WIB") }
  var isAllDay by remember(initialTask) { mutableStateOf(initialTask?.isAllDay ?: false) }
  var priority by remember(initialTask) { mutableStateOf(initialTask?.priority ?: TaskPriority.HIGH) }
  val transcript = initialTask?.voiceTranscript ?: "Buat tugas instalasi server OSPF tanggal lima jam delapan prioritas tinggi"

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = SurfaceContainerLowest,
    dragHandle = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, bottom = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(width = 40.dp, height = 4.dp)
            .clip(CircleShape)
            .background(OutlineVariant)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier
            .clip(CircleShape)
            .background(PrimaryFixed)
            .padding(horizontal = 10.dp, vertical = 3.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(13.dp)
          )
          Text(
            text = "Ditangkap via Suara Offline",
            color = Primary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    },
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 6.dp)
        .testTag("task_review_sheet"),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(
            text = "Review Tugas",
            color = OnSurface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "Periksa dan edit tugas Anda",
            color = OnSurfaceVariant,
            fontSize = 13.sp
          )
        }

        IconButton(
          onClick = { /* Quick voice trigger */ },
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(SurfaceContainerLow)
        ) {
          Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "Voice Input",
            tint = Primary,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      // 1. Judul Tugas
      Column {
        Text(
          text = "Judul Tugas",
          color = OnSurfaceVariant,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("task_title_input"),
          trailingIcon = {
            Icon(
              imageVector = Icons.Default.Edit,
              contentDescription = null,
              tint = Primary,
              modifier = Modifier.size(18.dp)
            )
          },
          shape = RoundedCornerShape(10.dp),
          colors = TextFieldDefaults.colors(
            focusedContainerColor = SurfaceContainerLow,
            unfocusedContainerColor = SurfaceContainerLow,
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = OutlineVariant
          )
        )
      }

      // 2. Kategori Selector Chips
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Kategori",
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "Model: TKJ Dasar",
            color = Primary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          val categories = listOf("TKJ", "Kelompok", "Pribadi", "Umum")
          categories.forEach { cat ->
            val isSelected = category.equals(cat, ignoreCase = true)
            val chipBg = when {
              isSelected && cat == "TKJ" -> PrimaryContainer
              isSelected && cat == "Kelompok" -> Color(0xFFA64DCC)
              isSelected && cat == "Pribadi" -> Secondary
              isSelected -> Color(0xFF19343E)
              else -> SurfaceContainerLow
            }
            val chipText = when {
              isSelected -> Color.White
              else -> OnSurfaceVariant
            }

            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(chipBg)
                .clickable { category = cat }
                .padding(horizontal = 12.dp, vertical = 6.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = cat,
                  color = chipText,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
                if (isSelected) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }
            }
          }
        }
      }

      // 3. Tanggal & Waktu
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Tanggal
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Tanggal",
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
          )
          OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            singleLine = true,
            leadingIcon = {
              Icon(
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(18.dp)
              )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
              focusedContainerColor = SurfaceContainerLow,
              unfocusedContainerColor = SurfaceContainerLow
            )
          )
        }

        // Waktu
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Waktu",
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
          )
          OutlinedTextField(
            value = if (isAllDay) "-- : --" else time,
            onValueChange = { time = it },
            enabled = !isAllDay,
            singleLine = true,
            leadingIcon = {
              Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null,
                tint = if (isAllDay) Outline else Primary,
                modifier = Modifier.size(18.dp)
              )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
              focusedContainerColor = SurfaceContainerLow,
              unfocusedContainerColor = SurfaceContainerLow
            )
          )
        }
      }

      // 4. Sepanjang hari Switch
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(SurfaceContainerLow)
          .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.HourglassEmpty,
            contentDescription = null,
            tint = OnSurfaceVariant,
            modifier = Modifier.size(20.dp)
          )
          Column {
            Text(
              text = "Sepanjang hari",
              color = OnSurface,
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "Nonaktifkan jam spesifik",
              color = Outline,
              fontSize = 11.sp
            )
          }
        }

        Switch(
          checked = isAllDay,
          onCheckedChange = { isAllDay = it },
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Primary,
            uncheckedThumbColor = Outline,
            uncheckedTrackColor = SurfaceContainerHighest
          ),
          modifier = Modifier.testTag("switch_all_day")
        )
      }

      // 5. Prioritas Selector (Tinggi, Sedang, Rendah)
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
          text = "Prioritas",
          color = OnSurfaceVariant,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf(
            Triple(TaskPriority.HIGH, "Tinggi", PriorityHigh),
            Triple(TaskPriority.MEDIUM, "Sedang", PriorityMedium),
            Triple(TaskPriority.LOW, "Rendah", PriorityLow)
          ).forEach { (p, label, color) ->
            val isSelected = priority == p
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) color else Color.Transparent)
                .clickable { priority = p }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Flag,
                  contentDescription = null,
                  tint = if (isSelected) Color.White else Outline,
                  modifier = Modifier.size(15.dp)
                )
                Text(
                  text = label,
                  color = if (isSelected) Color.White else Outline,
                  fontSize = 13.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              }
            }
          }
        }
      }

      // Speech transcription footnote badge
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(SurfaceContainerHigh.copy(alpha = 0.6f))
          .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Outlined.Verified,
          contentDescription = null,
          tint = Secondary,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "\"$transcript\"",
          color = OnSurfaceVariant,
          fontSize = 11.sp,
          fontStyle = FontStyle.Italic,
          maxLines = 1
        )
      }

      // 6. Action Buttons: Batal & Simpan
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = SurfaceContainerHigh,
            contentColor = OnSurface
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("review_cancel_button")
        ) {
          Text(text = "Batal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(
          onClick = {
            onSave(title, category, date, time, isAllDay, priority, transcript)
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryContainer,
            contentColor = Color.White
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("review_save_button")
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Simpan", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
