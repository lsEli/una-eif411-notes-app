package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.repository.INoteRepository
import javax.inject.Inject


class UpdateNoteUseCase @Inject constructor(private val repository: INoteRepository) {
    suspend operator fun invoke(id: Long, title: String, content: String) =
        repository.update(id, title, content)
}