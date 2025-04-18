package com.android.noteit.utils

import android.graphics.Paint // Needed for strike-through
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat // Needed for colors
import androidx.recyclerview.widget.RecyclerView
import com.android.noteit.R
import com.android.noteit.datamodels.TodoListModel

class TodoListAdapter(
    // Use List for flexibility, val for listeners as they usually don't change
    private var todos: List<TodoListModel>, // Expecting a PRE-FILTERED list (only non-archived items)
    private val onCheckBoxToggled: (item: TodoListModel, isChecked: Boolean) -> Unit,
    private val onClick: (item: TodoListModel, position: Int) -> Unit // Added position for more context
) : RecyclerView.Adapter<TodoListAdapter.ItemViewHolder>() {

    // ViewHolder using the CORRECT IDs from your task_template.xml
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Corrected ID to match task_template.xml
        val tvTitle: TextView = view.findViewById(R.id.taskTitle)
        // Checkbox ID was already correct
        val cbIsDone: CheckBox = view.findViewById(R.id.checkBox)

        // Item click listener setup
        init {
            itemView.setOnClickListener {
                // Use adapterPosition for safety, check for NO_POSITION
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Ensure access within list bounds before calling lambda
                    if (position < todos.size) {
                        onClick(todos[position], position) // Call the lambda passed in constructor
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Inflates your provided task_template.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_template, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Returns size of the pre-filtered list
        return todos.size
    }

    // onBindViewHolder - Fully implemented
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Get the item for the current position
        val currentItem = todos[position]

        // --- Bind Data ---
        holder.tvTitle.text = currentItem.title
        holder.cbIsDone.setOnCheckedChangeListener(null)
        holder.cbIsDone.isChecked = currentItem.isDone
        updateUiForCompletion(holder, currentItem.isDone)
        holder.cbIsDone.setOnCheckedChangeListener { _, isChecked ->
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < todos.size) {
                val clickedItem = todos[adapterPosition]
                updateUiForCompletion(holder, isChecked)
                onCheckBoxToggled(clickedItem, isChecked)
            }
        }
    }

    // Helper function to update UI based on completion status
    private fun updateUiForCompletion(holder: ItemViewHolder, isCompleted: Boolean) {
        val context = holder.itemView.context
        val currentFlags = holder.tvTitle.paintFlags
        if (isCompleted) {
            if ((currentFlags and Paint.STRIKE_THRU_TEXT_FLAG) == 0) {
                holder.tvTitle.paintFlags = currentFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray))
        } else {
            if ((currentFlags and Paint.STRIKE_THRU_TEXT_FLAG) != 0) {
                holder.tvTitle.paintFlags = currentFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black)) // Changed to black for better readability
        }
    }

    // Method to update the list (used by fragment's onResume or LiveData observer)
    fun updateList(newFilteredList: List<TodoListModel>) {
        this.todos = newFilteredList
        notifyDataSetChanged()
    }

}
