package cr.ac.una.notesapplication.presentation.note.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.domain.usecase.note.AddNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.ObserveNoteByIdUseCase
import cr.ac.una.notesapplication.domain.usecase.note.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class NoteEditUiState(
    val isEdit: Boolean = false,
    val title: String = "",
    val content: String = "",
    val canSave: Boolean = false
)

class NoteEditViewModel(
    private val id: Long?,
    private val observeById: ObserveNoteByIdUseCase,
    private val add: AddNoteUseCase,
    private val update: UpdateNoteUseCase
) : ViewModel() {
    private val title = MutableStateFlow("")
    private val content = MutableStateFlow("")
    private val loaded = MutableStateFlow(false)

    val uiState: StateFlow<NoteEditUiState> = combine(title, content, loaded) { t, c, isLoaded ->
        val isEdit = id != null
        val canSave = t.trim().isNotEmpty() && c.trim().isNotEmpty()
        NoteEditUiState(
            isEdit = isEdit,
            title = t,
            content = c,
            canSave = canSave && (isEdit.not() || isLoaded) // evita guardar antes de cargar
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NoteEditUiState())

    init {
        if (id != null) {
            viewModelScope.launch {
                observeById(id).collect { note ->
                    if (note != null && !loaded.value) {
                        title.value = note.title
                        content.value = note.content
                        loaded.value = true
                    }
                }
            }
        } else {
            loaded.value = true
        }
    }

    fun onTitleChange(v: String) {
        title.value = v
    }

    fun onContentChange(v: String) {
        content.value = v
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val t = title.value
            val c = content.value
            if (id == null) add(t, c) else update(id, t, c)
            onDone()
        }
    }
}