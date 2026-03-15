package cr.ac.una.notesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cr.ac.una.notesapplication.core.di.AppContainer
import cr.ac.una.notesapplication.presentation.navigation.AppNavGraph
import cr.ac.una.notesapplication.ui.theme.NotesApplicationTheme


class MainActivity : ComponentActivity() {
    private val container = AppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesApplicationTheme { AppNavGraph(container) }
        }
    }
}