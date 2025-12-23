package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.ui.NoteEditScreen
import com.example.notesapp.ui.NoteListScreen
import com.example.notesapp.ui.Screen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val application = application as NotesApplication
                    NotesApp(
                        viewModelFactory = NoteViewModelFactory(application.repository)
                    )
                }
            }
        }
    }
}

@Composable
fun NotesApp(viewModelFactory: NoteViewModelFactory) {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = Screen.NoteList.route
    ) {
        composable(Screen.NoteList.route) {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = { note ->
                    navController.navigate(Screen.NoteEdit.createRoute(note.id))
                },
                onAddNoteClick = {
                    navController.navigate(Screen.NoteEdit.createRoute())
                }
            )
        }

        composable(
            route = Screen.NoteEdit.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            NoteEditScreen(
                viewModel = viewModel,
                noteId = if (noteId > 0) noteId else null,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onDelete = {
                    navController.popBackStack()
                }
            )
        }
    }
}
