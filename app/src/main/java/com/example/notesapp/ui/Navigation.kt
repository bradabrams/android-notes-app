package com.example.notesapp.ui

sealed class Screen(val route: String) {
    data object NoteList : Screen("note_list")
    data object NoteEdit : Screen("note_edit/{noteId}") {
        fun createRoute(noteId: Long? = null): String {
            return "note_edit/${noteId ?: -1}"
        }
    }
}
