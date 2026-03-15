package cr.ac.una.notesapplication.presentation.note.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.usecase.note.ObserveNoteByIdUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class NoteDetailUiState(val note: Note? = null)

class NoteDetailViewModel(
    id: Long, observeById: ObserveNoteByIdUseCase
) : ViewModel() {

    val uiState: StateFlow<NoteDetailUiState> = observeById(id).map { NoteDetailUiState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NoteDetailUiState())
}