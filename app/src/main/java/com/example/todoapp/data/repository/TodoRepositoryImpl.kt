package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.mapper.TodoMapper
import com.example.todoapp.domain.entity.Todo
import com.example.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos().map { models ->
            TodoMapper.run { models.toDomainList() }
        }
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)?.let {
            TodoMapper.run { it.toDomain() }
        }
    }


    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(TodoMapper.run { todo.toEntity() })
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.updateTodo(TodoMapper.run { todo.toEntity() })
    }

    override suspend fun deleteTodo(id: Int) {
        dao.deleteTodo(id)
    }

}