package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
  @Query("SELECT * FROM tasks ORDER BY isCompleted ASC, createdAt DESC")
  fun getAllTasks(): Flow<List<TaskEntity>>

  @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY createdAt DESC")
  fun getActiveTasks(): Flow<List<TaskEntity>>

  @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY createdAt DESC")
  fun getCompletedTasks(): Flow<List<TaskEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTask(task: TaskEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(tasks: List<TaskEntity>)

  @Update
  suspend fun updateTask(task: TaskEntity)

  @Delete
  suspend fun deleteTask(task: TaskEntity)

  @Query("UPDATE tasks SET isCompleted = :isCompleted, completedNote = :note WHERE id = :id")
  suspend fun updateTaskStatus(id: Long, isCompleted: Boolean, note: String?)

  @Query("SELECT COUNT(*) FROM tasks")
  suspend fun getTaskCount(): Int
}
