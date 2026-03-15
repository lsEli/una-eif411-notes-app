package cr.ac.una.notesapplication.core.app

import android.app.Application
import cr.ac.una.notesapplication.core.di.AppContainer


class NotesApp : Application() {
    // Vive mientras viva el proceso de la app (no se reinicia por rotación)
    val container: AppContainer by lazy { AppContainer() }
}