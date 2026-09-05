package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskEntity
import com.example.ui.ChatMessage
import com.example.ui.MessageSender
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantSheet(
  isVisible: Boolean,
  messages: List<ChatMessage>,
  onSendMessage: (String) -> Unit,
  onDismiss: () -> Unit,
  onQuickVoiceClick: () -> Unit
) {
  if (!isVisible) return

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var textInput by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = SurfaceContainerLowest,
    dragHandle = null,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.92f)
        .testTag("ai_assistant_sheet")
    ) {
      // 1. Header Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(PrimaryContainer)
          .padding(top = 8.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Grab handle
          Box(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
              .size(width = 36.dp, height = 4.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.4f))
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              // Cute Mini Robot Badge with Live Status
              Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                  modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Box(
                    modifier = Modifier
                      .size(40.dp)
                      .clip(CircleShape)
                      .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = Icons.Default.SmartToy,
                      contentDescription = "Asisten AI",
                      tint = Color.White,
                      modifier = Modifier.size(24.dp)
                    )
                  }
                }
                // Online dot
                Box(
                  modifier = Modifier
                    .size(11.dp)
                    .clip(CircleShape)
                    .background(SecondaryFixed)
                )
              }

              Column {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Text(
                    text = "Asisten AI",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Box(
                    modifier = Modifier
                      .clip(CircleShape)
                      .background(Color.White.copy(alpha = 0.2f))
                      .padding(horizontal = 6.dp, vertical = 1.dp)
                  ) {
                    Text(
                      text = "v1.2",
                      color = Color.White.copy(alpha = 0.9f),
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp),
                  modifier = Modifier.padding(top = 2.dp)
                ) {
                  Box(
                    modifier = Modifier
                      .size(5.dp)
                      .clip(CircleShape)
                      .background(SecondaryFixed)
                  )
                  Text(
                    text = "Offline • Siap membantu",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 11.sp
                  )
                }
              }
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Tutup",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }

      // 2. Chat Messages Stream
      LazyColumn(
        state = listState,
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
          .background(Color(0xFFF3FAFF))
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item {
          Spacer(modifier = Modifier.height(10.dp))
          // Date Separator Badge
          Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(SurfaceContainer)
                .padding(horizontal = 14.dp, vertical = 4.dp)
            ) {
              Text(
                text = "Hari ini, 05 September",
                color = OnSurfaceVariant,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
        }

        items(messages, key = { it.id }) { msg ->
          when (msg.sender) {
            MessageSender.USER -> UserMessageBubble(msg)
            MessageSender.BOT -> BotMessageBubble(msg)
          }
        }

        item {
          Spacer(modifier = Modifier.height(8.dp))
        }
      }

      // 3. Bottom Suggestion Chips & Input Bar
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(SurfaceContainerLowest)
          .padding(horizontal = 16.dp, vertical = 10.dp)
      ) {
        // Quick suggestion chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(
            Pair("Jadwal Hari Ini", Icons.Default.CalendarViewDay),
            Pair("Ide Cepat", Icons.Default.Lightbulb),
            Pair("Cek Pending", Icons.Default.Checklist)
          ).forEach { (prompt, icon) ->
            Row(
              modifier = Modifier
                .clip(CircleShape)
                .background(SurfaceContainerLow)
                .clickable { onSendMessage(prompt) }
                .padding(horizontal = 10.dp, vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = prompt,
                color = Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Main Input Controls
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Mic Button
          IconButton(
            onClick = onQuickVoiceClick,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(SurfaceContainer)
              .testTag("chat_mic_button")
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Suara",
              tint = Primary,
              modifier = Modifier.size(22.dp)
            )
          }

          // Input text
          OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            placeholder = {
              Text(
                text = "Ketik pesan atau perintah...",
                color = Outline,
                fontSize = 13.sp
              )
            },
            singleLine = true,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("chat_text_input"),
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
              focusedContainerColor = SurfaceContainerLow,
              unfocusedContainerColor = SurfaceContainerLow,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
              if (textInput.isNotEmpty()) {
                IconButton(onClick = { textInput = "" }) {
                  Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Hapus",
                    tint = Outline,
                    modifier = Modifier.size(16.dp)
                  )
                }
              }
            }
          )

          // Send Button
          IconButton(
            onClick = {
              if (textInput.isNotBlank()) {
                onSendMessage(textInput.trim())
                textInput = ""
              }
            },
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(PrimaryContainer)
              .testTag("chat_send_button")
          ) {
            Icon(
              imageVector = Icons.Default.Send,
              contentDescription = "Kirim",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun UserMessageBubble(message: ChatMessage) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.End
  ) {
    Card(
      shape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
      colors = CardDefaults.cardColors(containerColor = Primary),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
      modifier = Modifier.widthIn(max = 280.dp)
    ) {
      Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
        if (message.isVoiceTranscript) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(bottom = 2.dp)
          ) {
            Icon(
              imageVector = Icons.Outlined.GraphicEq,
              contentDescription = null,
              tint = Color.White.copy(alpha = 0.85f),
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Transkrip Suara",
              color = Color.White.copy(alpha = 0.85f),
              fontSize = 11.sp
            )
          }
        }
        Text(
          text = message.text,
          color = Color.White,
          fontSize = 14.sp,
          lineHeight = 20.sp
        )
      }
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(3.dp),
      modifier = Modifier.padding(top = 2.dp, end = 4.dp)
    ) {
      Text(text = message.time, color = Outline, fontSize = 11.sp)
      Icon(
        imageVector = Icons.Default.DoneAll,
        contentDescription = null,
        tint = Primary,
        modifier = Modifier.size(13.dp)
      )
    }
  }
}

@Composable
private fun BotMessageBubble(message: ChatMessage) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.Top
  ) {
    // Bot icon
    Box(
      modifier = Modifier
        .size(28.dp)
        .clip(CircleShape)
        .background(Primary),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.SmartToy,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(16.dp)
      )
    }

    Column(modifier = Modifier.weight(1f)) {
      Card(
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = message.text,
            color = OnSurface,
            fontSize = 14.sp,
            lineHeight = 20.sp
          )

          // Embedded structured task confirmation card if available
          message.taskCardPreview?.let { task ->
            Spacer(modifier = Modifier.height(10.dp))
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = task.title,
                    color = OnSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = null,
                    tint = Outline,
                    modifier = Modifier.size(16.dp)
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Outlined.CalendarToday,
                      contentDescription = null,
                      tint = Primary,
                      modifier = Modifier.size(14.dp)
                    )
                    Text(text = task.date, color = OnSurface, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                  }
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Outlined.Schedule,
                      contentDescription = null,
                      tint = Primary,
                      modifier = Modifier.size(14.dp)
                    )
                    Text(text = task.time, color = OnSurface, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .clip(CircleShape)
                      .background(SecondaryContainer)
                      .padding(horizontal = 8.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = task.category,
                      color = OnSecondaryContainer,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }

                  Text(
                    text = "Tersimpan Offline ✅",
                    color = Secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action chips: Edit Waktu, Tambah Alarm
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Box(
                modifier = Modifier
                  .clip(CircleShape)
                  .background(SurfaceContainer)
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Primary, modifier = Modifier.size(12.dp))
                  Text(text = "Edit Waktu", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
              }

              Box(
                modifier = Modifier
                  .clip(CircleShape)
                  .background(SurfaceContainer)
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = Primary, modifier = Modifier.size(12.dp))
                  Text(text = "Tambah Alarm", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
              }
            }
          }
        }
      }

      Text(
        text = message.time,
        color = Outline,
        fontSize = 11.sp,
        modifier = Modifier.padding(top = 2.dp, start = 4.dp)
      )
    }
  }
}
