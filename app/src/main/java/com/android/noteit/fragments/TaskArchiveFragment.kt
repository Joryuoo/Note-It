package com.android.noteit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.noteit.R
import com.android.noteit.activities.AddTaskActivity
import com.android.noteit.activities.OpenTaskActivity
import com.android.noteit.app.AppManager
import com.android.noteit.utils.TodoListAdapter


class TaskArchiveFragment : Fragment(R.layout.fragment_task_archive) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoAdapter: TodoListAdapter // Hold a reference to the adapter if needed later
    private lateinit var emptylistImg: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- RecyclerView Setup ---
        recyclerView = view.findViewById<RecyclerView>(R.id.arTask_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        emptylistImg = view.findViewById<ImageView>(R.id.isArchiveEmpty)

        val allTodos = AppManager.sessionUser?.taskList
//        val filteredTodos = allTodos?.filter { it.isArchived } ?: emptyList() // Provide empty list if null

        val filteredTodos = allTodos
            ?.filter { it.isArchived } // 1. Filter out archived
            ?.sortedBy { it.isDone }    // 2. Sort by isDone (false comes first)
            ?: emptyList()              // 3. Handle null case

        if(filteredTodos.isEmpty()){
            emptylistImg.visibility = View.VISIBLE
        } else{
            emptylistImg.visibility = View.GONE
        }

        // 2. Create the Adapter instance with callback implementations
        todoAdapter = TodoListAdapter(
            // Pass the filtered list
            todos = filteredTodos,
            // --- Checkbox Toggled Callback ---
            onCheckBoxToggled = { item, isChecked ->
                // This lambda is called when a checkbox state changes in the adapter
                Log.d("HomePageFragment2", "Checkbox toggled for item: ${item.title}, New state: $isChecked")

                val success = AppManager.updateTaskCompletionStatus(item.id, isChecked) // Assuming item has an 'id'
                if (!success) {
                    Toast.makeText(requireContext(), "Failed to update task status", Toast.LENGTH_SHORT).show()
                    Log.e("HomePageFragment2", "Failed to update task: ${item.title}")
                } else {
                    Log.d("HomePageFragment2", "Task status updated successfully for: ${item.title}")
                }
            },

            // --- Item Click Callback ---
            onClick = { item, position ->
                // This lambda is called when a whole item row is clicked in the adapter
                Log.d("HomePageFragment2", "Item clicked: ${item.title} at position $position")
                val intent = Intent(requireContext(), OpenTaskActivity::class.java)
                intent.putExtra(OpenTaskActivity.EXTRA_TASK_ID, item.id) // Assuming item has 'id' and constant exists
                startActivity(intent)
            }
        )
        recyclerView.adapter = todoAdapter

    }

    override fun onResume() {
        super.onResume()
        val allTodos = AppManager.sessionUser?.taskList
//        val filteredTodos = allTodos?.filter { it.isArchived } ?: emptyList()

        val filteredTodos = allTodos
            ?.filter { it.isArchived } // 1. Filter out archived
            ?.sortedBy { it.isDone }    // 2. Sort by isDone (false comes first)
            ?: emptyList()              // 3. Handle null case


        if (::todoAdapter.isInitialized) {
            todoAdapter.updateList(filteredTodos) // Assumes updateList uses notifyDataSetChanged
        } else {
            Log.w("HomePageFragment2", "Adapter not initialized in onResume")
        }

        if (filteredTodos != null) {
            if(filteredTodos.isEmpty()){
                emptylistImg.visibility = View.VISIBLE
            } else{

                emptylistImg.visibility = View.GONE
            }
        }
    }
}