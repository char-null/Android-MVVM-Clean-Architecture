package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.entity.Todo
import com.example.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo)
    }
}