package com.manu.todo_manager.src.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) var Id: Int = 0,
    var todoName: String,
    var todoDescription: String,
    var startDate: String,
    var endDate: String
)

@Entity(tableName = "completed_todo")
data class CompletedTodo(
    @PrimaryKey val id: Int,
    val todoName: String,
    val todoDescription: String
)
