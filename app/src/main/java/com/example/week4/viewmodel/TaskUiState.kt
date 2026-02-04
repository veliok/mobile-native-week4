package com.example.week4.viewmodel

import com.example.week4.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val filter: TaskFilter = TaskFilter.ALL,
)

enum class TaskFilter {
    ALL, COMPLETED
}