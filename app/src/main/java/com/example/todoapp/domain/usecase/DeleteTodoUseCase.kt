package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }
}