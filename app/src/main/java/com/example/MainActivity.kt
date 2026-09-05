package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppNavTab
import com.example.ui.TaskViewModel
import com.example.ui.components.AiAssistantSheet
import com.example.ui.components.OverflowDropdown
import com.example.ui.components.TaskReviewSheet
import com.example.ui.components.VoiceListeningSheet
import com.example.ui.components.XiuVocaTopBar
import com.example.ui.screens.CardShowcaseScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.ModelsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.XiuVocaTheme

class MainActivity : ComponentActivity() {
  private val viewModel: TaskViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      XiuVocaTheme {
        XiuVocaApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun XiuVocaApp(viewModel: TaskViewModel) {
  val context = LocalContext.current

  val allTasks by viewModel.allTasks.collectAsState()
  val activeTasks by viewModel.activeTasks.collectAsState()
  val completedTasks by viewModel.completedTasks.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val currentTab by viewModel.currentTab.collectAsState()
  val isVoiceListening by viewModel.isVoiceListeningActive.collectAsState()
  val voiceTranscript by viewModel.voiceTranscript.collectAsState()
  val isAssistantChatActive by viewModel.isAssistantChatActive.collectAsState()
  val chatMessages by viewModel.chatMessages.collectAsState()
  val isReviewSheetActive by viewModel.isReviewSheetActive.collectAsState()
  val editingTask by viewModel.editingTask.collectAsState()
  val isSplashVisible by viewModel.isSplashVisible.collectAsState()
  val isOverflowOpen by viewModel.isOverflowMenuOpen.collectAsState()
  val infoDialogMsg by viewModel.infoDialogMessage.collectAsState()
  val isCompletedExpanded by viewModel.isCompletedDrawerExpanded.collectAsState()

  var isShowcaseActive by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(PrimaryContainer)
      .statusBarsPadding()
  ) {
    Scaffold(
      topBar = {
        XiuVocaTopBar(
          onMoreOptionsClick = { viewModel.setOverflowMenuOpen(true) },
          onProfileClick = {
            viewModel.showInfoDialog(
              "Profil Pengguna:\n• Nama: Siswa TKJ & Pengembang\n• Mode AI: Offline Whisper Core\n• Database: Room SQLite Local"
            )
          }
        )
      },
      bottomBar = {
        NavigationBar(
          containerColor = SurfaceContainerLowest,
          tonalElevation = 6.dp,
          modifier = Modifier.testTag("bottom_nav_bar")
        ) {
          NavigationBarItem(
            selected = currentTab == AppNavTab.TASKS && !isShowcaseActive,
            onClick = {
              isShowcaseActive = false
              viewModel.setTab(AppNavTab.TASKS)
            },
            icon = {
              Icon(imageVector = Icons.Default.Checklist, contentDescription = "Tasks")
            },
            label = {
              Text(text = "Tasks", fontWeight = FontWeight.Medium, fontSize = 12.sp)
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Primary,
              selectedTextColor = Primary,
              indicatorColor = PrimaryFixed,
              unselectedIconColor = OnSurfaceVariant,
              unselectedTextColor = OnSurfaceVariant
            ),
            modifier = Modifier.testTag("nav_item_tasks")
          )

          NavigationBarItem(
            selected = isAssistantChatActive,
            onClick = {
              viewModel.openAssistantChat()
            },
            icon = {
              Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice Hub")
            },
            label = {
              Text(text = "Voice Hub", fontWeight = FontWeight.Medium, fontSize = 12.sp)
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Primary,
              selectedTextColor = Primary,
              indicatorColor = PrimaryFixed,
              unselectedIconColor = OnSurfaceVariant,
              unselectedTextColor = OnSurfaceVariant
            ),
            modifier = Modifier.testTag("nav_item_voice_hub")
          )

          NavigationBarItem(
            selected = (currentTab == AppNavTab.CATEGORIES || isShowcaseActive),
            onClick = {
              viewModel.setTab(AppNavTab.CATEGORIES)
            },
            icon = {
              Icon(imageVector = Icons.Default.Category, contentDescription = "Categories")
            },
            label = {
              Text(text = "Categories", fontWeight = FontWeight.Medium, fontSize = 12.sp)
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Primary,
              selectedTextColor = Primary,
              indicatorColor = PrimaryFixed,
              unselectedIconColor = OnSurfaceVariant,
              unselectedTextColor = OnSurfaceVariant
            ),
            modifier = Modifier.testTag("nav_item_categories")
          )

          NavigationBarItem(
            selected = currentTab == AppNavTab.MODELS,
            onClick = {
              isShowcaseActive = false
              viewModel.setTab(AppNavTab.MODELS)
            },
            icon = {
              Icon(imageVector = Icons.Default.Memory, contentDescription = "Models")
            },
            label = {
              Text(text = "Models", fontWeight = FontWeight.Medium, fontSize = 12.sp)
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Primary,
              selectedTextColor = Primary,
              indicatorColor = PrimaryFixed,
              unselectedIconColor = OnSurfaceVariant,
              unselectedTextColor = OnSurfaceVariant
            ),
            modifier = Modifier.testTag("nav_item_models")
          )
        }
      },
      containerColor = SurfaceContainerLow
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        if (isShowcaseActive) {
          CardShowcaseScreen(
            onBack = { isShowcaseActive = false },
            onVoiceTrigger = { viewModel.openVoiceSession() }
          )
        } else {
          when (currentTab) {
            AppNavTab.TASKS -> {
              TasksScreen(
                totalCount = allTasks.size,
                activeCount = allTasks.count { !it.isCompleted },
                completedCount = allTasks.count { it.isCompleted },
                activeTasks = activeTasks,
                completedTasks = completedTasks,
                isCompletedExpanded = isCompletedExpanded,
                onToggleCompletedDrawer = { viewModel.toggleCompletedDrawer() },
                onMicClick = { viewModel.openVoiceSession() },
                onTaskClick = { task -> viewModel.openAddOrEditTask(task) },
                onToggleTaskComplete = { task -> viewModel.toggleTaskCompleted(task) },
                onAddTaskClick = { viewModel.openAddOrEditTask(null) },
                onFilterClick = {
                  viewModel.showInfoDialog(
                    "Filter Kategori:\nSaat ini menampilkan semua tugas aktif. Buka tab 'Categories' untuk memfilter berdasarkan TKJ, Kelompok, atau Pribadi."
                  )
                }
              )
            }

            AppNavTab.VOICE_HUB -> {
              // Voice Hub tab triggers AI Assistant directly or opens Tasks
              TasksScreen(
                totalCount = allTasks.size,
                activeCount = allTasks.count { !it.isCompleted },
                completedCount = allTasks.count { it.isCompleted },
                activeTasks = activeTasks,
                completedTasks = completedTasks,
                isCompletedExpanded = isCompletedExpanded,
                onToggleCompletedDrawer = { viewModel.toggleCompletedDrawer() },
                onMicClick = { viewModel.openVoiceSession() },
                onTaskClick = { task -> viewModel.openAddOrEditTask(task) },
                onToggleTaskComplete = { task -> viewModel.toggleTaskCompleted(task) },
                onAddTaskClick = { viewModel.openAddOrEditTask(null) },
                onFilterClick = { }
              )
            }

            AppNavTab.CATEGORIES -> {
              CategoriesScreen(
                allTasks = allTasks,
                selectedCategory = selectedCategory,
                onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                onOpenShowcase = { isShowcaseActive = true },
                onToggleTask = { task -> viewModel.toggleTaskCompleted(task) },
                onTaskClick = { task -> viewModel.openAddOrEditTask(task) },
                onAddTask = { viewModel.openAddOrEditTask(null) }
              )
            }

            AppNavTab.MODELS -> {
              ModelsScreen(
                onTestVoice = { viewModel.openVoiceSession() }
              )
            }
          }
        }
      }
    }

    // Overflow Dropdown Popup (Screen 6)
    OverflowDropdown(
      expanded = isOverflowOpen,
      onDismissRequest = { viewModel.setOverflowMenuOpen(false) },
      onActionClick = { action ->
        when (action) {
          "Pengaturan" -> {
            viewModel.showInfoDialog(
              "Pengaturan XiuVoca:\n• Bahasa: Indonesia (id-ID)\n• Model Suara: Vosk Tiny-ID\n• Latensi Target: <120ms\n• Simpanan Offline: Aktif"
            )
          }
          "Tentang" -> {
            viewModel.showSplash(true)
          }
          "Bantuan" -> {
            viewModel.showInfoDialog(
              "Panduan Penggunaan XiuVoca:\n1. Tekan tombol Mic bulat di tengah untuk mendikte tugas.\n2. AI offline akan mengonversi suara menjadi teks & mengenali kategori otomatis.\n3. Tekan 'Selesai' untuk meninjau dan menyimpan tugas."
            )
          }
          "Beri Rating" -> {
            Toast.makeText(context, "Terima kasih atas penilaian Anda untuk XiuVoca!", Toast.LENGTH_SHORT).show()
          }
          "Bagikan" -> {
            Toast.makeText(context, "Tautan aplikasi XiuVoca disalin!", Toast.LENGTH_SHORT).show()
          }
          "Keluar" -> {
            Toast.makeText(context, "XiuVoca tersimpan aman secara offline.", Toast.LENGTH_SHORT).show()
          }
        }
      }
    )

    // Screen 2: Active Voice Listening Sheet
    VoiceListeningSheet(
      isVisible = isVoiceListening,
      transcriptText = voiceTranscript,
      onDismiss = { viewModel.closeVoiceSession() },
      onFinish = { viewModel.finishVoiceSessionAndReview() }
    )

    // Screen 3: AI Assistant Conversation Sheet
    AiAssistantSheet(
      isVisible = isAssistantChatActive,
      messages = chatMessages,
      onSendMessage = { query -> viewModel.sendUserMessage(query) },
      onDismiss = { viewModel.closeAssistantChat() },
      onQuickVoiceClick = {
        viewModel.closeAssistantChat()
        viewModel.openVoiceSession()
      }
    )

    // Screen 5: Task Review / Add / Edit Sheet
    TaskReviewSheet(
      isVisible = isReviewSheetActive,
      initialTask = editingTask,
      onDismiss = { viewModel.closeReviewSheet() },
      onSave = { title, category, date, time, isAllDay, priority, transcript ->
        viewModel.saveTask(title, category, date, time, isAllDay, priority, transcript)
        Toast.makeText(context, "Tugas berhasil disimpan offline!", Toast.LENGTH_SHORT).show()
      }
    )

    // Screen 4: Splash Screen Overlay
    AnimatedVisibility(
      visible = isSplashVisible,
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      SplashScreen(
        onDismiss = { viewModel.showSplash(false) },
        onReplay = { viewModel.showSplash(true) }
      )
    }

    // Info Alert Dialog for Overflow actions
    if (infoDialogMsg != null) {
      AlertDialog(
        onDismissRequest = { viewModel.showInfoDialog(null) },
        title = {
          Text(text = "XiuVoca Offline AI", fontWeight = FontWeight.Bold)
        },
        text = {
          Text(text = infoDialogMsg ?: "")
        },
        confirmButton = {
          TextButton(onClick = { viewModel.showInfoDialog(null) }) {
            Text(text = "OK", color = Primary, fontWeight = FontWeight.Bold)
          }
        },
        containerColor = SurfaceContainerLowest
      )
    }
  }
}
