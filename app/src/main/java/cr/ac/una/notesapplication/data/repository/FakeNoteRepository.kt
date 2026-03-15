package cr.ac.una.notesapplication.data.repository

import cr.ac.una.notesapplication.domain.model.Note
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update


class FakeNoteRepository : INoteRepository {
    private val notes = MutableStateFlow(
        listOf(
            Note(1, "Note 1", "This is the first note.", now(), now()),
            Note(2, "Note 2", "This is the second note.", now(), now()),
            Note(3, "Note 3", "This is the third note.", now(), now())
        )
    )

    private var nextId: Long = 4L

    override fun observeAll(): Flow<List<Note>> = notes

    override fun observeById(id: Long): Flow<Note?> =
        notes.map { list -> list.firstOrNull { it.id == id } }

    override suspend fun add(title: String, content: String): Long {
        val id = nextId++
        val timestamp = now()
        val newNote = Note(id, title.trim(), content.trim(), timestamp, timestamp)

        notes.update { it + newNote }

        return id
    }

    override suspend fun update(id: Long, title: String, content: String): Boolean {
        var updated = false

        notes.update { list ->
            list.map { note ->
                if (note.id == id) {
                    updated = true
                    note.copy(
                        title = title.trim(), content = content.trim(), updatedAt = now()
                    )
                } else {
                    note
                }
            }
        }

        return updated
    }

    override suspend fun delete(id: Long): Boolean {
        val before = notes.value.size

        notes.update { it.filterNot { note -> note.id == id } }

        return notes.value.size != before
    }

    private fun now(): Long = System.currentTimeMillis()
}