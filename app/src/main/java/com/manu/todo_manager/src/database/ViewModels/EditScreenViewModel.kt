package com.manu.todo_manager.src.database.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.manu.todo_manager.src.database.AppDataBase
import com.manu.todo_manager.src.database.Todo
import kotlinx.coroutines.launch

class EditScreenViewModel(application: Application): AndroidViewModel(application){
    val todoDao = AppDataBase.getInstance(application).todoDao()
    fun updateTodo(todo: Todo){
        viewModelScope.launch {
            todoDao.updateTodo(todo)
        }
    }
}