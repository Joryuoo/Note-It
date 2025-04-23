package com.android.noteit.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.fragments.HomePageFragment1
import com.android.noteit.fragments.HomePageFragment2
import com.android.noteit.fragments.HomePageFragment3
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream

class HomepageActivity : AppCompatActivity() {
    lateinit var btnProfile: de.hdodenhof.circleimageview.CircleImageView
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
        btnProfile = findViewById(R.id.btnProfileIcon)

        if (AppManager.sessionUser?.profilepictureUri == null) {
            btnProfile.setImageResource(R.drawable.ic_profile_icon)
            Log.e("Profile", "uri is null")
        } else {
            Log.e("Homepage", "Loading with glide")

            val uri = AppManager.sessionUser?.profilepictureUri
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.ic_profile_icon)
                    .into(btnProfile)
            } else {
                btnProfile.setImageResource(R.drawable.ic_profile_icon)
            }
        }


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




        btnProfile.setOnClickListener {
            Log.e("Homepage", "Profile icon clicked")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }

    fun copyUriToInternalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, "profile_picture_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onResume() {
        super.onResume()

        if (AppManager.sessionUser?.profilepictureUri == null) {
            btnProfile.setImageResource(R.drawable.ic_profile_icon)
            Log.e("Profile", "uri is null")
        } else {
            Log.e("Homepage", "Loading with glide")

            val uri = AppManager.sessionUser?.profilepictureUri
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.ic_profile_icon)
                    .into(btnProfile)
            } else {
                btnProfile.setImageResource(R.drawable.ic_profile_icon)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(this)  // Save data when the activity is paused
    }
}