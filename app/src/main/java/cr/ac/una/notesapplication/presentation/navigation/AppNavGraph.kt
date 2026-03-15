package cr.ac.una.notesapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cr.ac.una.notesapplication.core.app.NotesApp
import cr.ac.una.notesapplication.presentation.note.list.NoteListScreen
import cr.ac.una.notesapplication.core.di.AppContainer
import cr.ac.una.notesapplication.presentation.note.detail.NoteDetailScreen
import cr.ac.una.notesapplication.presentation.note.edit.NoteEditScreen


@Composable
fun AppNavGraph() {
    val nav = rememberNavController()

    // Tomamos el container desde Application (singleton)
    val context = LocalContext.current
    val container = remember(context) {
        (context.applicationContext as NotesApp).container
    }

    NavHost(navController = nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            NoteListScreen(
                container = container,
                onAdd = { nav.navigate(Routes.edit(null)) },
                onOpen = { id -> nav.navigate(Routes.detail(id)) },
                onEdit = { id -> nav.navigate(Routes.edit(id)) })
        }

        composable(
            route = Routes.DETAIL, arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("id") ?: return@composable

            NoteDetailScreen(
                container = container,
                id = id,
                onBack = { nav.popBackStack() },
                onEdit = { nav.navigate(Routes.edit(id)) })
        }

        composable(
            route = Routes.EDIT, arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) {
            val raw = it.arguments?.getLong("id") ?: -1L
            val id: Long? = raw.takeIf { v -> v > 0 }

            NoteEditScreen(
                container = container, id = id, onDone = { nav.popBackStack() })
        }
    }
}