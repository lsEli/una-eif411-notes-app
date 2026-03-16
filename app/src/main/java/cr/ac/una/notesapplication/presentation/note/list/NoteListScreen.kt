package cr.ac.una.notesapplication.presentation.note.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cr.ac.una.notesapplication.core.di.AppContainer
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    container: AppContainer, onAdd: () -> Unit, onOpen: (Long) -> Unit, onEdit: (Long) -> Unit
) {
    // Factory manual (simple)
    val viewModel: NoteListViewModel = viewModel(
        factory = NoteListViewModelFactory(container)
    )
    val state by viewModel.uiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.notes)) }) }, floatingActionButton = {
        FloatingActionButton(onClick = onAdd) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_note))
        }
    }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.items, key = { it.id }) { note ->
                    NoteRow(
                        note = note,
                        onOpen = { onOpen(note.id) },
                        onEdit = { onEdit(note.id) },
                        onDelete = { viewModel.delete(note.id) })
                }
            }
        }
    }
}

@Composable
private fun NoteRow(
    note: Note, onOpen: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.weight(1f)) {
                Text(note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(note.content, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                }
            }
        }
    }
}