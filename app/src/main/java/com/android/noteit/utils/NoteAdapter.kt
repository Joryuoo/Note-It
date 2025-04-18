package com.android.noteit.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.noteit.R
import com.android.noteit.datamodels.NoteModel

class NoteAdapter (
    private var noteList: ArrayList<NoteModel>,
    private val onClick: (NoteModel) -> Unit

    ):RecyclerView.Adapter<NoteAdapter.ItemViewHolder>(){

    class ItemViewHolder(view : View): RecyclerView.ViewHolder(view){
        val noteTitle = view.findViewById<TextView>(R.id.noteTitle)
        val content = view.findViewById<TextView>(R.id.noteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_template, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = noteList[position]

        holder.noteTitle.setText(item.title)
        holder.content.setText(item.content)
        holder.itemView.setOnClickListener {
            onClick(item)
        }

    }


    fun updateNotes(newNotes: ArrayList<NoteModel>) {
        noteList = newNotes
        notifyDataSetChanged()
    }
}