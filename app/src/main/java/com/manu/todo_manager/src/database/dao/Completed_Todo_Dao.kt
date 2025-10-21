package com.manu.todo_manager.src.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.todo_manager.src.database.CompletedTodo
import kotlinx.coroutines.flow.Flow

@Dao
interface Completed_Todo_Dao{
    @Query("select * from completed_todo order by id asc")
    fun getCompletedTodos(): Flow<List<CompletedTodo>>

    @Query("delete from completed_todo where id = :id")
    suspend fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completedTodo: CompletedTodo)
}