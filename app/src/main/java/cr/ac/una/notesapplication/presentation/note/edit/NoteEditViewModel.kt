package cr.ac.una.notesapplication.presentation.note.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.notesapplication.R
import cr.ac.una.notesapplication.domain.usecase.note.CreateNoteUseCase
import cr.ac.una.notesapplication.domain.usecase.note.GetNoteByIdUseCase
import cr.ac.una.notesapplication.domain.usecase.note.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteEditUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isEdit: Boolean = false,
    val title: String = "",
    val content: String = "",
    val canSave: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNoteById: GetNoteByIdUseCase,
    private val createNote: CreateNoteUseCase,
    private val updateNote: UpdateNoteUseCase
) : ViewModel() {
    companion object {
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
    }

    private val id: Long? = savedStateHandle.get<Long>(KEY_ID)?.takeIf { it > 0 }

    private val title = savedStateHandle.getStateFlow(KEY_TITLE, "")
    private val content = savedStateHandle.getStateFlow(KEY_CONTENT, "")
    private val isLoading = MutableStateFlow(id != null)
    private val isSaving = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<NoteEditUiState> =
        combine(title, content, isLoading, isSaving, error) { t, c, loading, saving, err ->
            val isEdit = id != null
            val hasValidFields = t.trim().isNotEmpty() && c.trim().isNotEmpty()

            NoteEditUiState(
                isLoading = loading,
                isSaving = saving,
                isEdit = isEdit,
                title = t,
                content = c,
                canSave = hasValidFields && !loading && !saving,
                error = err
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteEditUiState(isLoading = id != null, isEdit = id != null)
        )

    init {
        if (id != null) {
            loadNote()
        }
    }

    private fun loadNote() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                val note = getNoteById(id!!)

                if (note != null) {
                    val currentTitle = savedStateHandle.get<String>(KEY_TITLE).orEmpty()
                    val currentContent = savedStateHandle.get<String>(KEY_CONTENT).orEmpty()

                    if (currentTitle.isBlank() && currentContent.isBlank()) {
                        savedStateHandle[KEY_TITLE] = note.title
                        savedStateHandle[KEY_CONTENT] = note.content
                    }
                } else {
                    error.value = R.string.note_not_found.toString()
                }
            } catch (e: Exception) {
                error.value = e.message ?: R.string.note_load_error.toString()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onTitleChange(value: String) {
        savedStateHandle[KEY_TITLE] = value
    }

    fun onContentChange(value: String) {
        savedStateHandle[KEY_CONTENT] = value
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val currentState = uiState.value
            if (!currentState.canSave) return@launch

            isSaving.value = true
            error.value = null

            try {
                val t = title.value.trim()
                val c = content.value.trim()

                if (id == null) {
                    createNote(t, c)
                } else {
                    updateNote(id, t, c)
                }

                onDone()
            } catch (e: Exception) {
                error.value = e.message ?: R.string.note_save_error.toString()
            } finally {
                isSaving.value = false
            }
        }
    }
}