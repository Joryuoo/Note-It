package com.android.noteit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val btnProfile = findViewById<Button>(R.id.btnProfileIcon)

        btnProfile.setOnClickListener {
            Log.e("Homepage", "Profile icon clicked")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}