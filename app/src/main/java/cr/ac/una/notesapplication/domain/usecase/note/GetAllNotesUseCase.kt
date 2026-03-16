package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import javax.inject.Inject


class GetAllNotesUseCase @Inject constructor(private val repository: INoteRepository) {
    suspend operator fun invoke(): List<Note> = repository.getAll()
}