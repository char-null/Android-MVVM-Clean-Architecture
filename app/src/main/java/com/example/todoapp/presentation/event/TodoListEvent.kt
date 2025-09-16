package com.example.todoapp.presentation.event

import com.example.todoapp.domain.entity.Todo

sealed class TodoListEvent {
    object LoadTodos : TodoListEvent()
    data class DeleteTodo(val todo: Todo) : TodoListEvent()
    data class ToggleTodoComplete(val todo: Todo) : TodoListEvent()
}