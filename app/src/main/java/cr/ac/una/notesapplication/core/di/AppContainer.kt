package cr.ac.una.notesapplication.core.di

import cr.ac.una.notesapplication.data.repository.FakeNoteRepository
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import cr.ac.una.notesapplication.domain.usecase.note.AddNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.DeleteNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.ObserveAllNotesUseCase
import cr.ac.una.notesapplication.domain.usecase.note.ObserveNoteByIdUseCase
import cr.ac.una.notesapplication.domain.usecase.note.UpdateNoteUseCase

class AppContainer {
    val noteRepository: INoteRepository = FakeNoteRepository()
    val observeAllNotes = ObserveAllNotesUseCase(noteRepository)
    val observeNoteById = ObserveNoteByIdUseCase(noteRepository)
    val addNote = AddNoteUseCase(noteRepository)
    val updateNote = UpdateNoteUseCase(noteRepository)
    val deleteNote = DeleteNoteUseCase(noteRepository)
}