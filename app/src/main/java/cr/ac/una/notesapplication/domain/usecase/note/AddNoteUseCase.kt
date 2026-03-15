package cr.ac.una.notesapplication.domain.usecase.note

import cr.ac.una.notesapplication.domain.repository.INoteRepository


class AddNoteUseCase(private val repository: INoteRepository) {
    suspend operator fun invoke(title: String, content: String): Long =
        repository.add(title, content)
}