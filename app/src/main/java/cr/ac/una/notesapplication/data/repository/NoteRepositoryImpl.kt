package cr.ac.una.notesapplication.data.repository

import cr.ac.una.notesapplication.data.remote.api.NotesApi
import cr.ac.una.notesapplication.data.remote.dto.NoteRequestDto
import cr.ac.una.notesapplication.data.remote.mapper.toDomain
import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(
    private val api: NotesApi
) : INoteRepository {
    override suspend fun getAll(): List<Note> {
        return api.getNotes().map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Note? {
        return try {
            api.getNoteById(id).toDomain()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun create(title: String, content: String): Note {
        return api.createNote(
            NoteRequestDto(
                title = title, content = content
            )
        ).toDomain()
    }

    override suspend fun update(id: Long, title: String, content: String): Note {
        return api.updateNote(
            id = id, request = NoteRequestDto(
                title = title, content = content
            )
        ).toDomain()
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            api.deleteNote(id)
            true
        } catch (_: Exception) {
            false
        }
    }
}