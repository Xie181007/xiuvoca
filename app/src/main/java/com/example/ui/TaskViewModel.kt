package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.TaskEntity
import com.example.data.TaskPriority
import com.example.data.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavTab(val label: String) {
  TASKS("Tasks"),
  VOICE_HUB("Voice Hub"),
  CATEGORIES("Categories"),
  MODELS("Models")
}

data class ChatMessage(
  val id: String = java.util.UUID.randomUUID().toString(),
  val sender: MessageSender,
  val text: String,
  val time: String,
  val isVoiceTranscript: Boolean = false,
  val taskCardPreview: TaskEntity? = null
)

enum class MessageSender {
  USER,
  BOT
}

class TaskViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: TaskRepository

  init {
    val db = AppDatabase.getInstance(application)
    repository = TaskRepository(db.taskDao())
    viewModelScope.launch {
      repository.seedInitialDataIfEmpty()
    }
  }

  val allTasks: StateFlow<List<TaskEntity>> = repository.allTasks
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _selectedCategory = MutableStateFlow<String?>(null)
  val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

  val activeTasks: StateFlow<List<TaskEntity>> = combine(allTasks, _selectedCategory) { tasks, cat ->
    val active = tasks.filter { !it.isCompleted }
    if (cat == null) active else active.filter { it.category.equals(cat, ignoreCase = true) }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val completedTasks: StateFlow<List<TaskEntity>> = combine(allTasks, _selectedCategory) { tasks, cat ->
    val completed = tasks.filter { it.isCompleted }
    if (cat == null) completed else completed.filter { it.category.equals(cat, ignoreCase = true) }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Navigation tab
  private val _currentTab = MutableStateFlow(AppNavTab.TASKS)
  val currentTab: StateFlow<AppNavTab> = _currentTab.asStateFlow()

  // Active voice listening sheet (Screen 2)
  private val _isVoiceListeningActive = MutableStateFlow(false)
  val isVoiceListeningActive: StateFlow<Boolean> = _isVoiceListeningActive.asStateFlow()

  private val _voiceTranscript = MutableStateFlow("")
  val voiceTranscript: StateFlow<String> = _voiceTranscript.asStateFlow()

  private val _isListening = MutableStateFlow(false)
  val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

  // AI Assistant Chat sheet (Screen 3)
  private val _isAssistantChatActive = MutableStateFlow(false)
  val isAssistantChatActive: StateFlow<Boolean> = _isAssistantChatActive.asStateFlow()

  private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage(
        sender = MessageSender.BOT,
        text = "Halo! 👋 Saya Asisten AI XiuVoca. Saya bisa membantu Anda mengelola tugas secara offline. Apa yang bisa saya bantu?",
        time = "08:58"
      ),
      ChatMessage(
        sender = MessageSender.USER,
        text = "Apa yang bisa kamu bantu?",
        time = "08:59"
      ),
      ChatMessage(
        sender = MessageSender.BOT,
        text = "Saya siap membantu Anda dalam:\n• Membuat tugas instan dari suara Anda\n• Mengatur jadwal & pengingat waktu\n• Mengkategorikan tugas secara otomatis\n\nCukup tekan tombol mic dan sebutkan tugas Anda!",
        time = "08:59"
      ),
      ChatMessage(
        sender = MessageSender.USER,
        text = "\"Buat tugas besok jam 9 rapat kelompok\"",
        time = "09:00",
        isVoiceTranscript = true
      ),
      ChatMessage(
        sender = MessageSender.BOT,
        text = "Tugas baru berhasil dibuat dan disimpan offline! Pengingat disetel otomatis 15 menit sebelum acara dimulai.",
        time = "09:00",
        taskCardPreview = TaskEntity(
          title = "Rapat kelompok",
          category = "Kelompok",
          date = "Besok, 06 Sep 2026",
          time = "09:00 WIB",
          isAllDay = false,
          priority = TaskPriority.MEDIUM,
          isCompleted = false
        )
      )
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  // Review / Add / Edit Task sheet (Screen 5)
  private val _isReviewSheetActive = MutableStateFlow(false)
  val isReviewSheetActive: StateFlow<Boolean> = _isReviewSheetActive.asStateFlow()

  private val _editingTask = MutableStateFlow<TaskEntity?>(null)
  val editingTask: StateFlow<TaskEntity?> = _editingTask.asStateFlow()

  // Splash screen overlay (Screen 4)
  private val _isSplashVisible = MutableStateFlow(false)
  val isSplashVisible: StateFlow<Boolean> = _isSplashVisible.asStateFlow()

  // Overflow menu open state (Screen 6)
  private val _isOverflowMenuOpen = MutableStateFlow(false)
  val isOverflowMenuOpen: StateFlow<Boolean> = _isOverflowMenuOpen.asStateFlow()

  // Active dialog message (e.g. from overflow menu or help)
  private val _infoDialogMessage = MutableStateFlow<String?>(null)
  val infoDialogMessage: StateFlow<String?> = _infoDialogMessage.asStateFlow()

  // Completed items accordion expanded/collapsed
  private val _isCompletedDrawerExpanded = MutableStateFlow(false)
  val isCompletedDrawerExpanded: StateFlow<Boolean> = _isCompletedDrawerExpanded.asStateFlow()

  private var voiceTypingJob: Job? = null

  fun setTab(tab: AppNavTab) {
    _currentTab.value = tab
    if (tab == AppNavTab.VOICE_HUB) {
      _isAssistantChatActive.value = true
    }
  }

  fun selectCategory(category: String?) {
    _selectedCategory.value = category
  }

  fun toggleCompletedDrawer() {
    _isCompletedDrawerExpanded.value = !_isCompletedDrawerExpanded.value
  }

  fun setOverflowMenuOpen(open: Boolean) {
    _isOverflowMenuOpen.value = open
  }

  fun showInfoDialog(msg: String?) {
    _infoDialogMessage.value = msg
  }

  fun showSplash(show: Boolean) {
    _isSplashVisible.value = show
  }

  fun openVoiceSession() {
    _isVoiceListeningActive.value = true
    _isListening.value = true
    _voiceTranscript.value = ""

    // Animate speech recognition typewriter stream
    voiceTypingJob?.cancel()
    voiceTypingJob = viewModelScope.launch {
      val fullSentence = "Besok jam 8 pagi instalasi server OSPF, terus hari Jumat depan kumpul laporan kelompok 5"
      val words = fullSentence.split(" ")
      val builder = StringBuilder()
      for (word in words) {
        delay(180)
        builder.append(word).append(" ")
        _voiceTranscript.value = builder.toString().trim()
      }
    }
  }

  fun closeVoiceSession() {
    voiceTypingJob?.cancel()
    _isVoiceListeningActive.value = false
    _isListening.value = false
  }

  fun finishVoiceSessionAndReview() {
    voiceTypingJob?.cancel()
    _isVoiceListeningActive.value = false
    _isListening.value = false
    val transcript = _voiceTranscript.value.ifBlank {
      "Instalasi server OSPF tanggal lima jam delapan prioritas tinggi"
    }
    // Prepare task for review
    _editingTask.value = TaskEntity(
      title = "Instalasi server OSPF",
      category = "TKJ",
      date = "05 Sep 2026",
      time = "08:00 WIB",
      isAllDay = false,
      priority = TaskPriority.HIGH,
      isCompleted = false,
      voiceTranscript = transcript
    )
    _isReviewSheetActive.value = true
  }

  fun openAddOrEditTask(task: TaskEntity? = null) {
    _editingTask.value = task ?: TaskEntity(
      title = "",
      category = "TKJ",
      date = "05 Sep 2026",
      time = "08:00 WIB",
      isAllDay = false,
      priority = TaskPriority.HIGH,
      isCompleted = false,
      voiceTranscript = "Perintah suara lokal offline"
    )
    _isReviewSheetActive.value = true
  }

  fun closeReviewSheet() {
    _isReviewSheetActive.value = false
    _editingTask.value = null
  }

  fun saveTask(
    title: String,
    category: String,
    date: String,
    time: String,
    isAllDay: Boolean,
    priority: TaskPriority,
    voiceTranscript: String?
  ) {
    viewModelScope.launch {
      val existing = _editingTask.value
      if (existing != null && existing.id > 0) {
        repository.updateTask(
          existing.copy(
            title = title,
            category = category,
            date = date,
            time = time,
            isAllDay = isAllDay,
            priority = priority,
            voiceTranscript = voiceTranscript
          )
        )
      } else {
        repository.insertTask(
          TaskEntity(
            title = title.ifBlank { "Tugas Baru" },
            category = category,
            date = date,
            time = if (isAllDay) "Sepanjang hari" else time,
            isAllDay = isAllDay,
            priority = priority,
            isCompleted = false,
            voiceTranscript = voiceTranscript
          )
        )
      }
      _isReviewSheetActive.value = false
      _editingTask.value = null
    }
  }

  fun toggleTaskCompleted(task: TaskEntity) {
    viewModelScope.launch {
      repository.toggleTaskCompleted(task)
    }
  }

  fun deleteTask(task: TaskEntity) {
    viewModelScope.launch {
      repository.deleteTask(task)
    }
  }

  fun openAssistantChat() {
    _isAssistantChatActive.value = true
  }

  fun closeAssistantChat() {
    _isAssistantChatActive.value = false
  }

  fun sendUserMessage(text: String) {
    if (text.isBlank()) return
    val userMsg = ChatMessage(
      sender = MessageSender.USER,
      text = text,
      time = "Sekarang"
    )
    _chatMessages.value = _chatMessages.value + userMsg

    // Simulate AI Local response
    viewModelScope.launch {
      delay(600)
      val lower = text.lowercase()
      val botReply = when {
        lower.contains("jadwal") -> {
          ChatMessage(
            sender = MessageSender.BOT,
            text = "Berikut jadwal hari ini:\n1. 08:00 - Instalasi server OSPF (TKJ)\n2. 14:00 - Konfigurasi Mikrotik VLAN Lab 3\nSemua proses tersimpan dalam database offline Anda.",
            time = "Sekarang"
          )
        }
        lower.contains("ide") -> {
          ChatMessage(
            sender = MessageSender.BOT,
            text = "💡 Ide dicatat! Anda dapat menyimpannya ke kategori 'Pribadi' atau membuat tugas baru kapan saja.",
            time = "Sekarang"
          )
        }
        lower.contains("pending") || lower.contains("belum") -> {
          val activeCount = allTasks.value.count { !it.isCompleted }
          ChatMessage(
            sender = MessageSender.BOT,
            text = "Saat ini terdapat $activeCount tugas yang masih aktif/belum selesai. Anda dapat meninjau daftar di tab Tasks.",
            time = "Sekarang"
          )
        }
        lower.contains("buat") || lower.contains("tugas") -> {
          val newTask = TaskEntity(
            title = text.replace("buat", "", true).replace("tugas", "", true).trim().capitalizeFirst(),
            category = if (lower.contains("kelompok")) "Kelompok" else "TKJ",
            date = "Besok",
            time = "09:00 WIB",
            priority = TaskPriority.MEDIUM,
            isCompleted = false
          )
          repository.insertTask(newTask)
          ChatMessage(
            sender = MessageSender.BOT,
            text = "Tugas baru berhasil dibuat dan disimpan ke database offline!",
            time = "Sekarang",
            taskCardPreview = newTask
          )
        }
        else -> {
          ChatMessage(
            sender = MessageSender.BOT,
            text = "Saya mengerti! Perintah \"$text\" telah diproses secara lokal tanpa koneksi internet.",
            time = "Sekarang"
          )
        }
      }
      _chatMessages.value = _chatMessages.value + botReply
    }
  }

  private fun String.capitalizeFirst(): String {
    return if (isNotEmpty()) this[0].uppercaseChar() + substring(1) else this
  }
}
