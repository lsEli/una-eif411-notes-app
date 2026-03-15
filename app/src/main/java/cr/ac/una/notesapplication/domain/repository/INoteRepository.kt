package cr.ac.una.notesapplication.domain.repository

import cr.ac.una.notesapplication.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface INoteRepository {
    fun observeAll(): Flow<List<Note>>
    fun observeById(id: Long): Flow<Note?>
    suspend fun add(title: String, content: String): Long
    suspend fun update(id: Long, title: String, content: String): Boolean
    suspend fun delete(id: Long): Boolean
}