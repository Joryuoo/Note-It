package com.android.noteit.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.android.noteit.R
import com.android.noteit.app.AppManager


class HomePageFragment3 : Fragment(R.layout.fragment_home_page3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNotesArchive = view.findViewById<Button>(R.id.notesArchive)
        val btnTodosArchive =  view.findViewById<Button>(R.id.taskArchive)
        val archiveNotesFragment = NotesArchiveFragment()
        val archiveTaskFragment = TaskArchiveFragment()
        val defaultColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
        val toggledColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)

        btnNotesArchive.setOnClickListener {
            if (toggledColorStateList != null) { toggleButton(btnNotesArchive, toggledColorStateList) }
            if (defaultColorStateList != null) { unToggleButton(btnTodosArchive, defaultColorStateList) }

            childFragmentManager.beginTransaction().apply {
                replace(R.id.flArchiveFragment, archiveNotesFragment)
                addToBackStack("notes archive")
                commit()
            }
        }

        btnTodosArchive.setOnClickListener {
            if (toggledColorStateList != null) { toggleButton(btnTodosArchive, toggledColorStateList) }
            if (defaultColorStateList != null) { unToggleButton(btnNotesArchive, defaultColorStateList) }

            childFragmentManager.beginTransaction().apply {
                replace(R.id.flArchiveFragment, archiveTaskFragment)
                addToBackStack("todo archive")
                commit()
            }
        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.flArchiveFragment, archiveNotesFragment)
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

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(requireContext())  // Save data when the activity is paused
    }
}
