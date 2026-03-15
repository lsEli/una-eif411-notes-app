package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow


class ObserveNoteByIdUseCase(private val repository: INoteRepository) {
    operator fun invoke(id: Long): Flow<Note?> = repository.observeById(id)
}