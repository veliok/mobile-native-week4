package com.example.week4.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.week4.model.Task

@Composable
fun TaskCard(
    task: Task,
    showDate: Boolean = true,
    showCheckbox: Boolean = true,
    showDelete: Boolean = true,
    onClick: () -> Unit = {},
    onToggle: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    // Kortin väri muuttuu tilasta riippuen
    val containerColor = if (task.done) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }
    // Kortin läpinäkyvyys vaihtuu tilasta riippuen
    val alpha = if (task.done) 0.6f else 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (task.done) 0.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .alpha(alpha)
        ) {
            if (showCheckbox) {
                Checkbox(
                    checked = task.done,
                    onCheckedChange = { onToggle() }
                )
            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (showDate) {
                    Text(
                        text = task.dueDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Poistonappi
            if (showDelete) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete task",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}