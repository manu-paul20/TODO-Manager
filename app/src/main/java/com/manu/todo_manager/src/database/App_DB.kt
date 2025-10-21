package com.manu.todo_manager.src.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manu.todo_manager.src.database.dao.Completed_Todo_Dao
import com.manu.todo_manager.src.database.dao.TodoDao

@Database(entities = [Todo::class,CompletedTodo::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase(){
    abstract fun todoDao(): TodoDao
    abstract fun completedTodoDao(): Completed_Todo_Dao

    companion object{
        @Volatile private var Instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase{
            return Instance?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDataBase::class.java,
                    name = "app_db"
                ).build()
                Instance = instance
                instance
            }
        }
    }
}