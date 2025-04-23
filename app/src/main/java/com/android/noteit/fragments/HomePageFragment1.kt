package com.android.noteit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.noteit.activities.AddNoteActivity
import com.android.noteit.R
import com.android.noteit.activities.OpenNoteActivity
import com.android.noteit.app.AppManager
import com.android.noteit.datamodels.NoteModel
import com.android.noteit.utils.NoteAdapter

class HomePageFragment1 : Fragment(R.layout.fragment_home_page1) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyImg: ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        emptyImg = view.findViewById(R.id.isScreenEmpty)

        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            startActivity(intent)
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.home_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val notes = AppManager.sessionUser?.noteList?.filter { !it.isArchived }?.toCollection(ArrayList())

        if (notes != null) {
            if(notes.isEmpty()){
                emptyImg.visibility = View.VISIBLE
            } else{
                emptyImg.visibility = View.GONE
            }
        }
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
    }

    override fun onResume() {
        super.onResume()
        // Notify the adapter that data has changed.
//        recyclerView.adapter?.notifyDataSetChanged()

        val notes = AppManager.sessionUser?.noteList?.filter { !it.isArchived }?.toCollection(ArrayList())

        if (notes != null) {
            if(notes.isEmpty()){
                emptyImg.visibility = View.VISIBLE
            } else{
                emptyImg.visibility = View.GONE
            }
        }

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

    }

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(requireContext())  // Save data when the activity is paused
    }

}
