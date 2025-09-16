package com.example.todoapp.domain.repository

import com.example.todoapp.domain.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodos(): Flow<List<Todo>>
    suspend fun getTodoById(id: Int): Todo?
    suspend fun insertTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(id: Int)
}