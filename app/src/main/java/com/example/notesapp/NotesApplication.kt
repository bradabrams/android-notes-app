package com.example.notesapp

import android.app.Application
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteRepository

class NotesApplication : Application() {
    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}
