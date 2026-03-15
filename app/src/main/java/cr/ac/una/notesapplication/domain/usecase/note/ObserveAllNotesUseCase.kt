package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow


class ObserveAllNotesUseCase(private val repository: INoteRepository) {
    operator fun invoke(): Flow<List<Note>> = repository.observeAll()
}