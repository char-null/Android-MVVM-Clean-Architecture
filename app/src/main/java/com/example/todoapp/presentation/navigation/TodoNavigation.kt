package com.example.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.presentation.ui.AddEditTodoScreen
import com.example.todoapp.presentation.ui.TodoListScreen

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "todo_list"
    ) {
        composable("todo_list") {
            TodoListScreen(
                onNavigateToAddTodo = {
                    navController.navigate("add_edit_todo/-1")
                },
                onNavigateToEditTodo = { todoId ->
                    navController.navigate("add_edit_todo/$todoId")
                }
            )
        }

        composable(
            route = "add_edit_todo/{todoId}",
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTodoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
