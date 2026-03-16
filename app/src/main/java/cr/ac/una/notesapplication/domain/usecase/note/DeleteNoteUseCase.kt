package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.repository.INoteRepository
import javax.inject.Inject


class DeleteNoteUseCase @Inject constructor(private val repository: INoteRepository) {
    suspend operator fun invoke(id: Long): Boolean = repository.delete(id)
}