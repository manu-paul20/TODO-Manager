package com.manu.todo_manager.src.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.manu.todo_manager.src.database.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao{
    @Query("select * from todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("delete from todo where id = :id")
    suspend fun deleteTodo(id: Int)

    @Update
    suspend fun updateTodo(todo: Todo)
}