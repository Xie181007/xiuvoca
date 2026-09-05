package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaskPriority(val label: String) {
  HIGH("Tinggi"),
  MEDIUM("Sedang"),
  LOW("Rendah")
}

@Entity(tableName = "tasks")
data class TaskEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val category: String, // TKJ, Kelompok, Pribadi, Umum
  val date: String,
  val time: String,
  val isAllDay: Boolean = false,
  val priority: TaskPriority = TaskPriority.MEDIUM,
  val isCompleted: Boolean = false,
  val completedNote: String? = null,
  val voiceTranscript: String? = null,
  val createdAt: Long = System.currentTimeMillis()
)
