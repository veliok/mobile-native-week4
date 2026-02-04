package com.example.week4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week4.viewmodel.TaskViewModel
import com.example.week4.view.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit = {},
    onNavigateHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    val grouped = uiState.tasks.groupBy { it.dueDate }

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Calendar") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go to list")
                }
            }
        )

        // Kalenterinäkymä (päiväryhmitys)
        LazyColumn {
            grouped.forEach { (date, tasks) ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = date, style = MaterialTheme.typography.titleMedium)

                            tasks.forEach { task ->
                                TaskCard(
                                    task = task,
                                    showDate = false,
                                    showCheckbox = false,
                                    showDelete = false,
                                    onClick = { onTaskClick(task.id) },
                                    onToggle = { viewModel.toggleDone(task.id) },
                                    onDelete = { viewModel.removeTask(task.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Editointidialogi
    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onClose = { viewModel.closeDialog() },
            onUpdate = { viewModel.updateTask(it) },
            onDelete = { viewModel.removeTask(it.id)}
        )
    }
}