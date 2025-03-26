package com.android.noteit.managers

object NoteManager {
    var notes = mutableListOf<Note>()

    fun addNote(title: String, content: String) {
        notes.add(Note(title, content))
    }
}

data class Note(val title: String, val content: String)
