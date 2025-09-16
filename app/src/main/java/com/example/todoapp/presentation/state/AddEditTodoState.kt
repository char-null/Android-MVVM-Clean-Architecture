package com.example.todoapp.presentation.state

data class AddEditTodoState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
