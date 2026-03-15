package cr.ac.una.notesapplication.presentation.note.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.ac.una.notesapplication.core.di.AppContainer


class NoteDetailViewModelFactory(
    private val container: AppContainer, private val id: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailViewModel(
            id = id,
            observeById = container.observeNoteById,
        ) as T
    }
}