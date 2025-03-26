package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.managers.UserManager.signed_in

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        var username = findViewById<TextView>(R.id.user_username)
        var email = findViewById<TextView>(R.id.user_email)

        username.text = signed_in?.username
        email.text = signed_in?.email


        val btnBackToMain = findViewById<ImageButton>(R.id.btnBack_to_main)
        btnBackToMain.setOnClickListener {
            Log.e("ProfileActivity", "Back to main menu")
            finish()
        }

        //if mu back
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        val btnLogout = findViewById<LinearLayout>(R.id.btn_sign_out)
        btnLogout.setOnClickListener{
            Log.e("Log out", "Log out btn")
            val intent = Intent(this, LogoutPageActivity::class.java)
            startActivity(intent)
        }

        val btnSettings = findViewById<LinearLayout>(R.id.btn_settings)
        btnSettings.setOnClickListener {
            Log.e("Settings btn", "Clicked")
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}