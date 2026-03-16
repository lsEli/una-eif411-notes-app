package cr.ac.una.notesapplication.presentation.note.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import cr.ac.una.notesapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    container: AppContainer, id: Long?, onDone: () -> Unit
) {
    val viewModel: NoteEditViewModel = viewModel(
        factory = NoteEditViewModelFactory(container, id)
    )
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (state.isEdit) (stringResource(R.string.edit_note)) else stringResource(R.string.new_note)) })
        }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.content,
                onValueChange = viewModel::onContentChange,
                label = { Text(stringResource(R.string.content)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.save(onDone) },
                enabled = state.canSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}