package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.entity.Todo
import com.example.todoapp.domain.usecase.AddTodoUseCase
import com.example.todoapp.domain.usecase.GetTodoByIdUseCase
import com.example.todoapp.domain.usecase.UpdateTodoUseCase
import com.example.todoapp.presentation.event.AddEditTodoEvent
import com.example.todoapp.presentation.state.AddEditTodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditTodoState())
    val state: StateFlow<AddEditTodoState> = _state.asStateFlow()

    private var currentTodoId: Int = 0
    private var isEditMode: Boolean = false

    init {
        savedStateHandle.get<Int>("todoId")?.let { todoId ->
            if (todoId != -1) {
                currentTodoId = todoId
                isEditMode = true
                loadTodo(todoId)
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.EnteredTitle -> {
                _state.value = _state.value.copy(title = event.title)
            }

            is AddEditTodoEvent.EnteredDescription -> {
                _state.value = _state.value.copy(description = event.description)
            }

            is AddEditTodoEvent.SaveTodo -> {
                saveTodo()
            }
        }
    }

    private fun loadTodo(todoId: Int) {
        viewModelScope.launch {
            try {
                val todo = getTodoByIdUseCase(todoId)
                todo?.let {
                    _state.value = _state.value.copy(
                        title = it.title,
                        description = it.description
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val todo = Todo(
                    id = if (isEditMode) currentTodoId else 0,
                    title = _state.value.title,
                    description = _state.value.description,
                    createdAt = if (isEditMode) System.currentTimeMillis() else System.currentTimeMillis()
                )

                if (isEditMode) {
                    updateTodoUseCase(todo)
                } else {
                    addTodoUseCase(todo)
                }

                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}