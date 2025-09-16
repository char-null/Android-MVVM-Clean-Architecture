package com.example.todoapp.presentation.state

import com.example.todoapp.domain.entity.Todo

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
