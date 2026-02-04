package com.example.week4.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week4.view.components.TaskCard
import com.example.week4.viewmodel.TaskFilter
import com.example.week4.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit = {},
    onAddClick: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {}
) {
    val uiState by taskViewModel.uiState.collectAsState()
    val selectedTask by taskViewModel.selectedTask.collectAsState()
    val addTaskFlag by taskViewModel.addTaskDialogVisible.collectAsState()
    val tasks = remember(uiState.tasks, uiState.filter) {
        taskViewModel.filteredTasks()
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(12.dp)
        ) {
            // Yläpalkki
            TopAppBar(
                title = { Text("Task List") },
                actions = {
                    IconButton(onClick = onNavigateCalendar) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Go to calendar"
                        )
                    }
                }
            )
            // Painikkeet
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onAddClick) { Text("New") }
                // Filtterit
                TaskFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = uiState.filter == filter,
                        onClick = { taskViewModel.setFilter(filter) },
                        label = { Text(filter.name) }
                    )
                }
                // Järjestä
                Button(onClick = { taskViewModel.sortByDueDate() }) {
                    Text("Sort")
                }
            }
            // Taskilista TaskItem Compose-funktiolla
            LazyColumn(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = tasks,
                    key = { it.id }
                ) { item ->
                    TaskCard(
                        task = item,
                        onClick = { onTaskClick(item.id)},
                        onToggle = { taskViewModel.toggleDone(item.id) },
                        onDelete = { taskViewModel.removeTask(item.id) }
                    )
                }
            }
            // Näytä editointidialogi taskia klikkaamalla
            if (selectedTask != null) {
                DetailDialog(
                    task = selectedTask!!, // Pakota non-null, voi crashata jos onkin null
                    onClose = { taskViewModel.closeDialog() },
                    onUpdate = { taskViewModel.updateTask(it) },
                    onDelete = { taskViewModel.removeTask(it.id)}
                )
            }
            // Näytä AddDialog, jos addTaskFlag = true
            if (addTaskFlag) {
                AddDialog(
                    onClose = { taskViewModel.addTaskDialogVisible.value = false },
                    onAdd = { taskViewModel.addTask(it) }
                )
            }
        }
    }
}