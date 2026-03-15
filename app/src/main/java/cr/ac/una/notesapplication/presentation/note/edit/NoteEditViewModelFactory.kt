package cr.ac.una.notesapplication.presentation.note.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.ac.una.notesapplication.core.di.AppContainer


class NoteEditViewModelFactory(
    private val container: AppContainer, private val id: Long?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteEditViewModel(
            id = id,
            observeById = container.observeNoteById,
            add = container.addNote,
            update = container.updateNote
        ) as T
    }
}