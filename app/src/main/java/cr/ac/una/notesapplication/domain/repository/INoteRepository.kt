package cr.ac.una.notesapplication.domain.repository

import cr.ac.una.notesapplication.domain.model.Note


interface INoteRepository {
    suspend fun getAll(): List<Note>
    suspend fun getById(id: Long): Note?
    suspend fun create(title: String, content: String): Note
    suspend fun update(id: Long, title: String, content: String): Note
    suspend fun delete(id: Long): Boolean
}