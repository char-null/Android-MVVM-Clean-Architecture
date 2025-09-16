package com.example.todoapp.data.mapper

import com.example.todoapp.data.model.TodoEntity
import com.example.todoapp.domain.entity.Todo

object TodoMapper {
    fun TodoEntity.toDomain(): Todo {
        return Todo(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt,
        )
    }

    fun Todo.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt,
        )
    }

    fun List<TodoEntity>.toDomainList(): List<Todo> {
        return map { it.toDomain() }
    }

    fun List<Todo>.toEntityList(): List<TodoEntity> {
        return map { it.toEntity() }
    }
}