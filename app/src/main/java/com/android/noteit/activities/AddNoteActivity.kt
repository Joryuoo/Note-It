package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.datamodels.NoteModel

class AddNoteActivity : AppCompatActivity() {
    private var noteIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note_screen)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etTitle = findViewById<EditText>(R.id.et_Title)
        val etNote = findViewById<EditText>(R.id.et_Content)

        noteIndex = intent.getIntExtra("note_index", -1)

        if (noteIndex != -1) {
            val note = AppManager.sessionUser?.noteList?.get(noteIndex!!)
            if (note != null) {
                etTitle.setText(note.title)
            }
            if (note != null) {
                etNote.setText(note.content)
            }
        }

        //for phone navigation back button
        onBackPressedDispatcher.addCallback(this){
            val title = etTitle.text.toString().trim()
            val content = etNote.text.toString().trim()

            if (title.isEmpty() && content.isEmpty()) {
                finish()
                return@addCallback
            }

            val finalTitle = if (title.isEmpty()) "Untitled" else title
            val finalContent = content

            if (noteIndex != -1) {
                var note = AppManager.sessionUser?.noteList?.get(noteIndex!!)
                if (note != null) {
                    note.updateNote(finalTitle, finalContent)
                }
            } else {
                AppManager.sessionUser?.noteList?.add(0, NoteModel(finalTitle, finalContent))

            }

            Toast.makeText(this@AddNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
            AppManager.saveAppData(this@AddNoteActivity)

            val resultIntent = Intent()
            resultIntent.putExtra("isNoteAdded", true)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnBack.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etNote.text.toString().trim()

            if (title.isEmpty() && content.isEmpty()) {
                finish()
                return@setOnClickListener
            }

            val finalTitle = if (title.isEmpty()) "Untitled" else title
            val finalContent = content

            if (noteIndex != -1) {
                var note = AppManager.sessionUser?.noteList?.get(noteIndex!!)
                if (note != null) {
                    note.updateNote(finalTitle, finalContent)
                }
            } else {
                // Add new note
                AppManager.sessionUser?.noteList?.add(0, NoteModel(finalTitle, finalContent))

            }

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            AppManager.saveAppData(this)

            val resultIntent = Intent()
            resultIntent.putExtra("isNoteAdded", true)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnDelete.setOnClickListener{
//            NoteManager.notes.indices
            if(AppManager.sessionUser?.noteList?.indices?.contains(noteIndex) == true){
                AppManager.sessionUser?.noteList?.removeAt(noteIndex!!)
                AppManager.saveAppData(this)
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

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(this)
    }
}
