package com.android.noteit

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {
    private var noteIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note_screen)
        val btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etTitle = findViewById<EditText>(R.id.et_Title)
        val etNote = findViewById<EditText>(R.id.et_Content)

        // Check if editing an existing note
        // default value -1
        noteIndex = intent.getIntExtra("note_index", -1)

        // if the index is > -1 mu edit rag existing na note
        // since ang index kay gi gi pass ra through intent
        if (noteIndex != -1) {
            // !! not-null assertion operator
            val note = NoteManager.notes[noteIndex!!]
            // load ang text sa edit text
            etTitle.setText(note.title)
            etNote.setText(note.content)
        }

        btnBack.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etNote.text.toString().trim()

            if (title.isEmpty() && content.isEmpty()) {
                finish() // Dili mu save og note na empty
                return@setOnClickListener
            }

            val finalTitle = if (title.isEmpty()) "Untitled" else title // if walay title then set it to "untitled"
            val finalContent = content // i set ra ang content bisag way sud

//            if noteIndex != -1 meaning ang note kay gi load ra from existing note na stored sa data class
            if (noteIndex != -1) {
                // Update existing note
                // !! not-null assertion operator
                NoteManager.notes[noteIndex!!] = Note(finalTitle, finalContent)
            } else {
                // Add new note
                NoteManager.addNote(finalTitle, finalContent)
            }

            val resultIntent = Intent()
            resultIntent.putExtra("isNoteAdded", true)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnDelete.setOnClickListener{
            if(noteIndex in NoteManager.notes.indices){
                NoteManager.notes.removeAt(noteIndex!!)
                finish()
            } else{
                finish()
            }
        }

        etTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                etNote.requestFocus() // Move to note EditText
                true
            } else {
                false
            }
        }
    }
}
