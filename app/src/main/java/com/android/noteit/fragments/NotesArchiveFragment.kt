package com.android.noteit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.noteit.R
import com.android.noteit.activities.OpenNoteActivity
import com.android.noteit.app.AppManager
import com.android.noteit.utils.NoteAdapter

class NotesArchiveFragment : Fragment(R.layout.fragment_notes_archive) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptylistImg: ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.arNotes_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        emptylistImg = view.findViewById<ImageView>(R.id.isArchiveEmpty)
        val notes = AppManager.sessionUser?.noteList?.filter { it.isArchived }?.toCollection(ArrayList())
//        recyclerView.adapter = AppManager.sessionUser?.noteList?.let { NoteAdapter(it) }
        recyclerView.adapter = notes?.let {
            NoteAdapter(
                it,
                onClick = { note ->
                    val position = AppManager.sessionUser?.noteList?.indexOf(note)
                    startActivity(Intent(requireContext(), OpenNoteActivity::class.java).apply {
                        putExtra("position", position)
                    })
                }
            )
        }

        if (notes != null) {
            if(!notes.isEmpty()){
                emptylistImg.visibility = View.GONE
            } else{
                emptylistImg.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Notify the adapter that data has changed.
//        recyclerView.adapter?.notifyDataSetChanged()

        val notes = AppManager.sessionUser?.noteList?.filter { it.isArchived }?.toCollection(ArrayList())

        if (recyclerView.adapter is NoteAdapter && notes != null) {
            (recyclerView.adapter as NoteAdapter).updateNotes(notes)
        } else if (notes != null) {
            recyclerView.adapter = NoteAdapter(notes) { note ->
                val position = AppManager.sessionUser?.noteList?.indexOf(note)
                startActivity(Intent(requireContext(), OpenNoteActivity::class.java).apply {
                    putExtra("position", position)
                })
            }
        }

        if (notes != null) {
            if(!notes.isEmpty()){
                emptylistImg.visibility = View.GONE
            } else{
                emptylistImg.visibility = View.VISIBLE
            }
        }
    }
}