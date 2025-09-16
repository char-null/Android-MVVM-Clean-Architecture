package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.entity.Todo
import com.example.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): Flow<List<Todo>> {
        return repository.getAllTodos()
    }
}