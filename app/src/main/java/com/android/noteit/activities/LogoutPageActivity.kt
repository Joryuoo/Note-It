package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R

class LogoutPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logout_page)

//        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val btnSignout = findViewById<Button>(R.id.btn_SignOut)
        btnSignout.setOnClickListener {
            Log.e("Sign out", "Clicked")
            Toast.makeText(this, "See you next time!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LandingActivity::class.java)

            //to close all previous activities
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val btnCancel = findViewById<Button>(R.id.btn_Cancel)
        btnCancel.setOnClickListener {
            Log.e("Cancel", "Cancel Sign out")
            finish()
        }
    }
}