package cr.ac.una.notesapplication.presentation.note.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.R
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.usecase.note.DeleteNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteListUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val items: List<Note> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllNotes: GetAllNotesUseCase,
    private val deleteNote: DeleteNoteUseCase
) : ViewModel() {

    companion object {
        private const val KEY_QUERY = "query"
    }

    private val query = savedStateHandle.getStateFlow(KEY_QUERY, "")
    private val notes = MutableStateFlow<List<Note>>(emptyList())
    private val isLoading = MutableStateFlow(true)
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<NoteListUiState> =
        combine(query, notes, isLoading, error) { q, list, loading, err ->
            val filtered = if (q.isBlank()) {
                list
            } else {
                val qq = q.trim().lowercase()
                list.filter {
                    it.title.lowercase().contains(qq) ||
                            it.content.lowercase().contains(qq)
                }
            }

            NoteListUiState(
                isLoading = loading,
                query = q,
                items = filtered,
                error = err
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState(isLoading = true)
        )

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                notes.value = getAllNotes()
            } catch (e: Exception) {
                error.value = e.message ?: R.string.notes_load_error.toString()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onQueryChange(value: String) {
        savedStateHandle[KEY_QUERY] = value
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            error.value = null

            try {
                deleteNote(id)
                loadNotes()
            } catch (e: Exception) {
                error.value = e.message ?: R.string.note_delete_error.toString()
            }
        }
    }
}