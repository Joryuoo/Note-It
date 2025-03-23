package com.android.noteit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment2 : Fragment(R.layout.fragment_home_page2) {
    private lateinit var noteContainer: LinearLayout

    override fun onResume() {
        super.onResume()
        displayNotes() // Refresh notes every time the fragment is resumed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        noteContainer = view.findViewById(R.id.ll_noteContainer)

        // Ensure the default note is added only once
        if (NoteManager.notes.isEmpty()) {
            NoteManager.addNote(
                "Welcome to Note It!",
                "Start organizing your thoughts, ideas, and tasks all in one place. Tap the + button to create your first note!"
            )
        }

        // Display notes dynamically
        displayNotes()

        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayNotes() {
        noteContainer.removeAllViews() // Clear previous views before adding new ones

        // .reverse() para ang last note added kay naas babaw
        for ((index, note) in NoteManager.notes.withIndex().reversed()) {
            addNoteToUI(note.title, note.content, index)
        }
    }

    private fun addNoteToUI(title: String, content: String, index: Int) {
        val noteView = layoutInflater.inflate(R.layout.note_template, noteContainer, false)

        noteView.findViewById<TextView>(R.id.noteTitle).text = title
        noteView.findViewById<TextView>(R.id.noteContent).text = truncateContent(content)

        // Open the note in AddNoteActivity when clicked
//        ma click gihapon bisag wala sa oncreate
        noteView.setOnClickListener {

            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            // pass through intent
            // pass index for checking
            intent.putExtra("note_index", index) // Pass index
            startActivity(intent)
        }

        noteContainer.addView(noteView)
    }

    private fun truncateContent(content: String): String {
        val words = content.split("\\s+".toRegex())
        val lines = content.split("\n")

        return when {
            words.size > 20 -> words.take(19).joinToString(" ") + "..."
            lines.size > 3 -> lines.take(3).joinToString("\n") + "..."
            else -> content
        }
    }
}