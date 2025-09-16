package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.entity.Todo
import com.example.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Int): Todo? {
        return repository.getTodoById(id)
    }
}