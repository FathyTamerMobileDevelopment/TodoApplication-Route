package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.model.Task

@Dao
interface TasksDao {
    @Insert
    fun createTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks(): MutableList<Task>

    @Query("select * from Task where date=:date order by time asc")
    fun getTasksByDate(date: Long): MutableList<Task>


}