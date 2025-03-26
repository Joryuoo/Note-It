package com.android.noteit.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.noteit.R
import com.android.noteit.managers.TaskModel

class HomePageFragment3 : Fragment(R.layout.fragment_home_page3) {
    private lateinit var taskContainer: LinearLayout
    private val taskModel = TaskModel()
    private lateinit var emptyImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskContainer = view.findViewById(R.id.ll_taskContainer)
        val buttonAddTask = view.findViewById<Button>(R.id.btnAdd)
        emptyImageView = view.findViewById(R.id.isEmpty)

        buttonAddTask.setOnClickListener {
            val bottomSheet = AddTaskBottomSheet { newTask ->
                taskModel.addTask(newTask)
                addTaskToUI(false, newTask)

                loadTasks()
                loadDoneTasks()
                updateEmptyState()
            }
            bottomSheet.show(parentFragmentManager, "AddTaskBottomSheet")

            loadTasks()
            loadDoneTasks()
            updateEmptyState()

        }

        loadTasks()
        loadDoneTasks()
        updateEmptyState()
    }

    private fun loadTasks() {
        taskContainer.removeAllViews()
        for ((index, task) in taskModel.tasks.withIndex()) {
            addTaskToUI(task.isDone, task.title)
        }
    }

    private fun loadDoneTasks() {
        for ((index, task) in taskModel.doneTasks.withIndex()) {
            addTaskToUI(task.isDone, task.title)
        }
    }

//    private fun addTaskToUI(isDone: Boolean, title: String) {
//        val taskView = layoutInflater.inflate(R.layout.task_template, taskContainer, false)
//        taskView.findViewById<TextView>(R.id.taskContent).text = title
//
//        val checkBox = taskView.findViewById<View>(R.id.checkBox)
//
//        if (isDone) {
//            // Change the color to black when the task is done
//            checkBox.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)
//        } else {
//            // Change the color to white when the task is not done
//            checkBox.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
//        }
//
//        taskContainer.addView(taskView)
//    }

    private fun addTaskToUI(isDone: Boolean, title: String) {

        val taskView = layoutInflater.inflate(R.layout.task_template, taskContainer, false)
        val taskContent = taskView.findViewById<TextView>(R.id.taskContent)
        val checkBox = taskView.findViewById<View>(R.id.checkBox)

        taskContent.text = title

        // Initial color based on isDone state
        checkBox.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (isDone) R.color.black else R.color.white
        )

        // Click listener for the checkbox
        checkBox.setOnClickListener {
            val task = taskModel.tasks.find { it.title == title } ?: taskModel.doneTasks.find { it.title == title }
            task?.let {
                it.isDone = !it.isDone  // Toggle isDone state

                // Move task between lists
                if (it.isDone) {
                    taskModel.doneTasks.add(it)
                    taskModel.tasks.remove(it)
                } else {
                    taskModel.tasks.add(it)
                    taskModel.doneTasks.remove(it)
                }

                // Refresh UI
                loadTasks()
                loadDoneTasks()
                updateEmptyState()
            }
        }

        taskContainer.addView(taskView)

        updateEmptyState()
    }



    private fun updateEmptyState() {
        emptyImageView.visibility = if (taskContainer.childCount == 0) View.VISIBLE else View.GONE
    }
}
