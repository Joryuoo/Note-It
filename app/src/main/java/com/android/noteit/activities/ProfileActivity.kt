package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.managers.UserManager.signed_in
import com.bumptech.glide.Glide
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var profilePicture: de.hdodenhof.circleimageview.CircleImageView
    @RequiresApi(Build.VERSION_CODES.P)
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

        username = findViewById(R.id.user_username)
        email = findViewById(R.id.user_email)
        profilePicture = findViewById(R.id.profilePicture)

        if (AppManager.sessionUser?.profilepictureUri == null) {
            profilePicture.setImageResource(R.drawable.profile_picture)
        } else {
            Glide.with(this)
                .load(AppManager.sessionUser?.profilepictureUri)
                .placeholder(R.drawable.profile_picture)
                .into(profilePicture)
        }
        username.text = AppManager.sessionUser?.username
        email.text = AppManager.sessionUser?.email

        val btnEditProfile = findViewById<LinearLayout>(R.id.btn_edit_profile)

        btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, EditProfileActivity::class.java))
        }


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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()

        if (AppManager.sessionUser?.profilepictureUri == null) {
            profilePicture.setImageResource(R.drawable.profile_picture)
        } else {
            Glide.with(this)
                .load(AppManager.sessionUser?.profilepictureUri)
                .placeholder(R.drawable.profile_picture)
                .into(profilePicture)
        }
        username.text = AppManager.sessionUser?.username
        email.text = AppManager.sessionUser?.email
    }

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(this)  // Save data when the activity is paused
    }


}