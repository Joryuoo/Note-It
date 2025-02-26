package com.android.noteit

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnNotes = findViewById<ImageButton>(R.id.btnNotes)
        val btnTask = findViewById<ImageButton>(R.id.btnTask)

        val homeFragment = HomePageFragment1()
        val notesFragment = HomePageFragment2()
        val taskFragment = HomePageFragment3()


        btnHome.setImageResource(R.drawable.selected_home_icon)
        btnNotes.setImageResource(R.drawable.default_notes_icon)
        btnTask.setImageResource(R.drawable.default_task_icon)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, homeFragment)
            commit()
        }

        btnHome.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                btnHome.setImageResource(R.drawable.selected_home_icon)
                btnNotes.setImageResource(R.drawable.default_notes_icon)
                btnTask.setImageResource(R.drawable.default_task_icon)

                replace(R.id.flFragment, homeFragment)
                addToBackStack("home")
                commit()
            }
        }

        btnNotes.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {

                btnHome.setImageResource(R.drawable.default_home_icon)
                btnNotes.setImageResource(R.drawable.selected_notes_icon)
                btnTask.setImageResource(R.drawable.default_task_icon)

                replace(R.id.flFragment, notesFragment)
                addToBackStack("notes")
                commit()
            }
        }

        btnTask.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                btnHome.setImageResource(R.drawable.default_home_icon)
                btnNotes.setImageResource(R.drawable.default_notes_icon)
                btnTask.setImageResource(R.drawable.selected_task_icon)
                replace(R.id.flFragment, taskFragment)
                addToBackStack("task")
                commit()
            }
        }


        val btnProfile = findViewById<Button>(R.id.btnProfileIcon)

        btnProfile.setOnClickListener {
            Log.e("Homepage", "Profile icon clicked")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}