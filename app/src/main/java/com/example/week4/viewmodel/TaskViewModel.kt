package com.example.week4.viewmodel

import com.example.week4.model.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    // Valittu task
    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    // Dialogi uudelle taskille
    val addTaskDialogVisible = MutableStateFlow<Boolean>(false)

    // Mock datan init
    init {
        _uiState.value = _uiState.value.copy(tasks = mockTasks)
    }

    // Funktiot
    fun openTask(id: Int) {
        val task = _uiState.value.tasks.find { it.id == id }
        _selectedTask.value = task
    }

    fun addTask(task: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks + task,
        )
    }

    fun toggleDone(id: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == id) { task.copy(done = ! task.done) }
                else { task }
            }
        )
    }

    fun updateTask(update: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map {
                if (it.id == update.id) update else it
            },
        )
        _selectedTask.value = null
    }

    fun removeTask(id: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != id }
        )
    }

    fun sortByDueDate() {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.sortedBy { it.dueDate }
        )
    }

    fun setFilter(filter: TaskFilter) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }

    // Palauttaa filtterin mukaisen taskilistan
    fun filteredTasks(): List<Task> {
        return when (_uiState.value.filter) {
            TaskFilter.ALL -> _uiState.value.tasks
            TaskFilter.COMPLETED -> _uiState.value.tasks.filter { it.done }
        }
    }

    fun closeDialog() {
        _selectedTask.value = null
    }
}