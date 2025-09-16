package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecase.DeleteTodoUseCase
import com.example.todoapp.domain.usecase.GetAllTodosUseCase
import com.example.todoapp.domain.usecase.UpdateTodoUseCase
import com.example.todoapp.presentation.event.TodoListEvent
import com.example.todoapp.presentation.state.TodoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase

) : ViewModel() {
    private val _state = MutableStateFlow(TodoListState())
    val state: StateFlow<TodoListState> = _state.asStateFlow()

    init {
        loadTodos()
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.LoadTodos -> {
                loadTodos()
            }

            is TodoListEvent.DeleteTodo -> {
                viewModelScope.launch {
                    try {
                        deleteTodoUseCase(event.todo.id)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = e.message)
                    }
                }
            }

            is TodoListEvent.ToggleTodoComplete -> {
                viewModelScope.launch {
                    try {
                        val updatedTodo = event.todo.copy(isCompleted = !event.todo.isCompleted)
                        updateTodoUseCase(updatedTodo)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = e.message)
                    }
                }
            }
        }
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                getAllTodosUseCase().collect { todos ->
                    _state.value = _state.value.copy(
                        todos = todos,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}