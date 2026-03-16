package cr.ac.una.notesapplication.data.remote.mapper

import cr.ac.una.notesapplication.data.remote.dto.NoteDto
import cr.ac.una.notesapplication.data.remote.dto.NoteRequestDto
import cr.ac.una.notesapplication.domain.model.Note


fun NoteDto.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Note.toRequestDto(): NoteRequestDto {
    return NoteRequestDto(
        title = title, content = content
    )
}