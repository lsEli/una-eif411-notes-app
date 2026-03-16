package cr.ac.una.notesapplication.data.remote.api

import cr.ac.una.notesapplication.data.remote.dto.NoteDto
import cr.ac.una.notesapplication.data.remote.dto.NoteRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface NotesApi {
    @GET("notes")
    suspend fun getNotes(): List<NoteDto>

    @GET("notes/{id}")
    suspend fun getNoteById(
        @Path("id") id: Long
    ): NoteDto

    @POST("notes")
    suspend fun createNote(
        @Body request: NoteRequestDto
    ): NoteDto

    @PUT("notes/{id}")
    suspend fun updateNote(
        @Path("id") id: Long, @Body request: NoteRequestDto
    ): NoteDto

    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Path("id") id: Long
    )
}