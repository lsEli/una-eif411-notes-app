package cr.ac.una.notesapplication.presentation.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.ac.una.notesapplication.core.di.AppContainer


class NoteListViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteListViewModel(
            observeNotes = container.observeAllNotes, deleteNote = container.deleteNote
        ) as T
    }
}