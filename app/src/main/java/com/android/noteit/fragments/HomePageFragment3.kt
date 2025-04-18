package com.android.noteit.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.android.noteit.R
import com.android.noteit.managers.TaskModel

class HomePageFragment3 : Fragment(R.layout.fragment_home_page3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNotesArchive = view.findViewById<Button>(R.id.notesArchive)
        val btnTodosArchive =  view.findViewById<Button>(R.id.taskArchive)
//        val flFragment = view.findViewById<FrameLayout>(R.id.flArchiveFragment)

        val archiveNotesFragment = NotesArchiveFragment()
        val archiveTaskFragment = TaskArchiveFragment()

        val defaultColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
        val toggledColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)

        btnNotesArchive.setOnClickListener {
            if (toggledColorStateList != null) { toggleButton(btnNotesArchive, toggledColorStateList) }
            if (defaultColorStateList != null) { unToggleButton(btnTodosArchive, defaultColorStateList) }

            childFragmentManager.beginTransaction().apply { // Use childFragmentManager
                replace(R.id.flArchiveFragment, archiveNotesFragment) // Use the created instance
                addToBackStack("notes archive")
                commit()
            }
        }

        btnTodosArchive.setOnClickListener {
            if (toggledColorStateList != null) { toggleButton(btnTodosArchive, toggledColorStateList) }
            if (defaultColorStateList != null) { unToggleButton(btnNotesArchive, defaultColorStateList) }

            childFragmentManager.beginTransaction().apply { // Use childFragmentManager
                replace(R.id.flArchiveFragment, archiveTaskFragment) // Use the created instance
                addToBackStack("todo archive")
                commit()
            }
        }

        childFragmentManager.beginTransaction().apply { // Use childFragmentManager
            replace(R.id.flArchiveFragment, archiveNotesFragment) // Use the created instance
            commit()
        }

        //initially
        if (toggledColorStateList != null) { toggleButton(btnNotesArchive, toggledColorStateList) }
        if (defaultColorStateList != null) { unToggleButton(btnTodosArchive, defaultColorStateList) }

    }

    fun toggleButton(button: Button, color: ColorStateList){
        val desiredWidthDp = 115f
        val metrics = requireContext().resources.displayMetrics
        val desiredWidthPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            desiredWidthDp,
            metrics
        ).toInt()

        val params = button.layoutParams
        params.width = desiredWidthPx
        button.layoutParams = params

        ViewCompat.setBackgroundTintList(button, color)
    }


    fun unToggleButton(button: Button, color: ColorStateList) {
        val params = button.layoutParams
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        button.layoutParams = params

        ViewCompat.setBackgroundTintList(button, color)
    }
}
