package com.android.noteit

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val btnDevelopers = findViewById<LinearLayout>(R.id.btn_developers)
        btnDevelopers.setOnClickListener{
            Log.e("Developer Btn", "Clicked")
            val intent = Intent(this,AboutDeveloperActivity::class.java)
            startActivity(intent)
        }
    }
}