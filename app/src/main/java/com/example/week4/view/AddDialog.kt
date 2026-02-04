package com.example.week4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.week4.model.Task

@Composable
fun AddDialog(
    onClose: () -> Unit,
    onAdd: (Task) -> Unit)
{
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Add Task") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("dueDate") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(
                    Task(
                        id = 0,
                        title = title,
                        description = description,
                        dueDate = dueDate,
                        done = false
                    )
                )
                onClose()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancel")
            }
        }
    )
}