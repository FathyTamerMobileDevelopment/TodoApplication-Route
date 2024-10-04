package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.TasksDao
import com.example.todoapp.database.model.Task
import com.example.todoapp.utils.DATABASE_NAME

@Database(entities = [Task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {
        var db: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return db!!
        }

         fun initDatabase(applicationContext: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return db!!
        }

    }

}