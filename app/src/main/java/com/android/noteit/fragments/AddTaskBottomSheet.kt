package com.android.noteit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.android.noteit.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheet(private val onTaskAdded: (String) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_task_bottom_sheet, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        view?.let {
            val parent = it.parent as View
            parent.setPadding(0, 0, 0, 0)
        }

        // Dismiss and send the task title when "actionDone" is pressed
        view?.findViewById<EditText>(R.id.task)?.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val taskTitle = textView.text.toString().trim()
                if (taskTitle.isNotEmpty()) {
                    onTaskAdded(taskTitle)
                    dismiss()
                }
                true
            } else false
        }
    }
}
