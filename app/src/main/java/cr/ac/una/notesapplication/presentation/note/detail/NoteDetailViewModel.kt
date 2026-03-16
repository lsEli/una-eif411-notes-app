package cr.ac.una.notesapplication.presentation.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.R
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.usecase.note.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NoteDetailUiState(
    val isLoading: Boolean = true, val note: Note? = null, val error: String? = null
)

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getNoteById: GetNoteByIdUseCase
) : ViewModel() {
    private val id: Long = checkNotNull(savedStateHandle["id"])
    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState: StateFlow<NoteDetailUiState> = _uiState.asStateFlow()

    init {
        loadNote()
    }

    private fun loadNote() {
        viewModelScope.launch {
            _uiState.value = NoteDetailUiState(isLoading = true)

            try {
                val note = getNoteById(id)

                _uiState.value = NoteDetailUiState(
                    isLoading = false, note = note, error = null
                )
            } catch (e: Exception) {
                _uiState.value = NoteDetailUiState(
                    isLoading = false,
                    note = null,
                    error = e.message ?: R.string.note_load_error.toString()
                )
            }
        }
    }
}