package com.manu.todo_manager.src.navigation

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.manu.todo_manager.src.database.Todo
import com.manu.todo_manager.src.ui.EditTodo
import com.manu.todo_manager.src.ui.Home
import com.manu.todo_manager.src.ui.ShowTodoDetails
import com.manu.todo_manager.src.ui.StartScreen

@Composable
fun App(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.StartScreen,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500)
            )
        }
    ) {
        composable<Routes.Home> { Home(navController = navController) }
        composable<Routes.TodoDetails>{
            val todo = it.toRoute<Routes.TodoDetails>()
            ShowTodoDetails(
                Todo(
                    todoName = todo.todoName,
                    todoDescription = todo.todoDestination,
                    startDate = todo.startDate,
                    endDate = todo.endDate
                ),
                navController
            )
        }
        composable<Routes.EditTodo> {
            val todo = it.toRoute<Routes.EditTodo>()
            EditTodo(
                todo = Todo(
                    Id = todo.id,
                    todoName = todo.todoName,
                    todoDescription = todo.todoDestination,
                    startDate = todo.startDate,
                    endDate = todo.endDate
                ),
                navController = navController
            )
        }
        composable<Routes.StartScreen> { StartScreen(navController) }
    }


}