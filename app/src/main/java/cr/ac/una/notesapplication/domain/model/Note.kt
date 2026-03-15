package cr.ac.una.notesapplication.domain.model


data class Note(
    val id: Long, val title: String, val content: String, val createdAt: Long, val updatedAt: Long
)
