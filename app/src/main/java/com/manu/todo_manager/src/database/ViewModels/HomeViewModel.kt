package com.manu.todo_manager.src.database.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.manu.todo_manager.src.database.AppDataBase
import com.manu.todo_manager.src.database.CompletedTodo
import com.manu.todo_manager.src.database.Todo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application){
    val todoDao = AppDataBase.Companion.getInstance(application).todoDao()
    val completedTodoDao = AppDataBase.Companion.getInstance(application).completedTodoDao()
    val todoList = todoDao.getAllTodos()
    val completedTodo = completedTodoDao.getCompletedTodos()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }

    fun insertTodo(todo: Todo){
        viewModelScope.launch {
            todoDao.insertTodo(todo)
        }
    }
    fun deleteTodo(id: Int){
        viewModelScope.launch {
            delay(200)
            todoDao.deleteTodo(id)
        }
    }
    fun deleteCompletedTodo(id: Int){
        viewModelScope.launch {
            completedTodoDao.delete(id)
        }
    }
    fun addCompletedTodo(completedTodo: CompletedTodo){
        viewModelScope.launch {
            completedTodoDao.insert(completedTodo)
        }
    }
}
