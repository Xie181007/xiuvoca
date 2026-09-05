package com.example.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
  val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()
  val activeTasks: Flow<List<TaskEntity>> = taskDao.getActiveTasks()
  val completedTasks: Flow<List<TaskEntity>> = taskDao.getCompletedTasks()

  suspend fun insertTask(task: TaskEntity): Long = taskDao.insertTask(task)

  suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

  suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

  suspend fun toggleTaskCompleted(task: TaskEntity) {
    val newStatus = !task.isCompleted
    val note = if (newStatus) "Baru saja diselesaikan" else null
    taskDao.updateTaskStatus(task.id, newStatus, note)
  }

  suspend fun seedInitialDataIfEmpty() {
    if (taskDao.getTaskCount() == 0) {
      val defaultTasks = listOf(
        // 8 Active tasks
        TaskEntity(
          title = "Instalasi server OSPF",
          category = "TKJ",
          date = "05 Sep 2026",
          time = "08:00",
          isAllDay = false,
          priority = TaskPriority.HIGH,
          isCompleted = false,
          voiceTranscript = "Buat tugas instalasi server OSPF tanggal lima jam delapan prioritas tinggi"
        ),
        TaskEntity(
          title = "Kumpul laporan kelompok",
          category = "Kelompok",
          date = "11 Sep 2026",
          time = "Sepanjang hari",
          isAllDay = true,
          priority = TaskPriority.MEDIUM,
          isCompleted = false,
          voiceTranscript = "Kumpul laporan kelompok sebelas september sepanjang hari"
        ),
        TaskEntity(
          title = "Olahraga pagi",
          category = "Pribadi",
          date = "Besok",
          time = "06:30",
          isAllDay = false,
          priority = TaskPriority.LOW,
          isCompleted = false,
          voiceTranscript = "Ingatkan olahraga pagi besok jam setengah tujuh"
        ),
        TaskEntity(
          title = "Konfigurasi Mikrotik VLAN Lab 3",
          category = "TKJ",
          date = "Hari ini",
          time = "14:00",
          isAllDay = false,
          priority = TaskPriority.HIGH,
          isCompleted = false
        ),
        TaskEntity(
          title = "Review Topologi Dynamic Routing OSPF",
          category = "TKJ",
          date = "Kamis",
          time = "13:30 WIB",
          isAllDay = false,
          priority = TaskPriority.MEDIUM,
          isCompleted = false
        ),
        TaskEntity(
          title = "Beli kabel UTP Cat6 2 Roll",
          category = "TKJ",
          date = "08 Sep 2026",
          time = "10:00",
          isAllDay = false,
          priority = TaskPriority.LOW,
          isCompleted = false
        ),
        TaskEntity(
          title = "Diskusi Presentasi Cloud Computing",
          category = "Kelompok",
          date = "Besok",
          time = "13:00",
          isAllDay = false,
          priority = TaskPriority.MEDIUM,
          isCompleted = false
        ),
        TaskEntity(
          title = "Review materi subnetting lanjutan",
          category = "TKJ",
          date = "12 Sep 2026",
          time = "15:00",
          isAllDay = false,
          priority = TaskPriority.LOW,
          isCompleted = false
        ),

        // 4 Completed tasks
        TaskEntity(
          title = "Review materi subnetting",
          category = "TKJ",
          date = "04 Sep 2026",
          time = "16:00",
          priority = TaskPriority.MEDIUM,
          isCompleted = true,
          completedNote = "Kemarin"
        ),
        TaskEntity(
          title = "Belanja kebutuhan mingguan",
          category = "Pribadi",
          date = "03 Sep 2026",
          time = "18:00",
          priority = TaskPriority.LOW,
          isCompleted = true,
          completedNote = "2 hari lalu"
        ),
        TaskEntity(
          title = "Presentasi jaringan komputer",
          category = "TKJ",
          date = "04 Sep 2026",
          time = "16:45",
          priority = TaskPriority.HIGH,
          isCompleted = true,
          completedNote = "Selesai 04 Sep 2026, 16:45"
        ),
        TaskEntity(
          title = "Konfigurasi DHCP MikroTik",
          category = "TKJ",
          date = "03 Sep 2026",
          time = "14:10",
          priority = TaskPriority.HIGH,
          isCompleted = true,
          completedNote = "Selesai 03 Sep 2026, 14:10"
        )
      )
      taskDao.insertAll(defaultTasks)
    }
  }
}
