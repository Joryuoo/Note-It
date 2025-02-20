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

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        val btnBackToMain = findViewById<Button>(R.id.btnBack_to_main)
        btnBackToMain.setOnClickListener {
            Log.e("ProfileActivity", "Back to main menu")
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if mu back
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }
}