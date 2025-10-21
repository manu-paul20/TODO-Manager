package com.manu.todo_manager.src.navigation
import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    data class TodoDetails(
        val todoName: String,
        val todoDestination: String,
        val startDate: String,
        val endDate: String
    )

    @Serializable
    object Home: Routes()

    @Serializable
    object StartScreen

    @Serializable
    data class EditTodo(
        val id : Int,
        val todoName: String,
        val todoDestination: String,
        val startDate: String,
        val endDate: String
    )
}