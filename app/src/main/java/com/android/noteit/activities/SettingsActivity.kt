package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val btnBackToProfile = findViewById<ImageButton>(R.id.btnBack_to_profile)
        btnBackToProfile.setOnClickListener {
            Log.e("Back button", "Clicked")
            finish()
        }

        val btnAboutApp = findViewById<LinearLayout>(R.id.about_note_it)
        btnAboutApp.setOnClickListener {
            Log.e("Developer Btn", "Clicked")
            val intent = Intent(this, AboutAppActivity::class.java)
            startActivity(intent)
        }

        val btnDevelopers = findViewById<LinearLayout>(R.id.btn_developers)
        btnDevelopers.setOnClickListener{
            Log.e("Developer Btn", "Clicked")
            val intent = Intent(this, AboutDeveloperActivity::class.java)
            startActivity(intent)
        }
    }
}