package cr.ac.una.notesapplication.presentation.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.usecase.note.DeleteNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.ObserveAllNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class NoteListUiState(
    val query: String = "", val items: List<Note> = emptyList()
)

class NoteListViewModel(
    private val observeNotes: ObserveAllNotesUseCase, private val deleteNote: DeleteNoteUseCase
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val notes = observeNotes()

    val uiState: StateFlow<NoteListUiState> = combine(query, notes) { q, list ->
        val filtered = if (q.isBlank()) list else {
            val qq = q.trim().lowercase()
            list.filter {
                it.title.lowercase().contains(qq) || it.content.lowercase().contains(qq)
            }
        }
        NoteListUiState(query = q, items = filtered)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NoteListUiState())

    fun onQueryChange(value: String) {
        query.value = value
    }

    fun delete(id: Long) {
        viewModelScope.launch { deleteNote(id) }
    }
}