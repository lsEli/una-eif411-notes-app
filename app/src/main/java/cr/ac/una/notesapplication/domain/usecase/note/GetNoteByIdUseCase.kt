package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import javax.inject.Inject


class GetNoteByIdUseCase @Inject constructor(private val repository: INoteRepository) {
    suspend operator fun invoke(id: Long): Note? = repository.getById(id)
}