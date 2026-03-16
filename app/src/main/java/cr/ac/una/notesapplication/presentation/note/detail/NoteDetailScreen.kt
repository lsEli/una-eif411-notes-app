package cr.ac.una.notesapplication.presentation.note.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cr.ac.una.notesapplication.core.di.AppContainer
import cr.ac.una.notesapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    container: AppContainer, id: Long, onBack: () -> Unit, onEdit: () -> Unit
) {
    val viewModel: NoteDetailViewModel = viewModel(
        factory = NoteDetailViewModelFactory(container, id)
    )
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.title)) }, navigationIcon = {
                TextButton(onClick = onBack) { Text(stringResource(R.string.back)) }
            }, actions = {
                TextButton(onClick = onEdit) { Text(stringResource(R.string.edit)) }
            })
        }) { pad ->
        val note = state.note
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (note == null) {
                Text(stringResource(R.string.note_not_found))
            } else {
                Text(note.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(10.dp))
                Text(note.content, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}