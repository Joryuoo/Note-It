package com.android.noteit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      para maka screenshot XD
        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)

        //loads splashscreen
        installSplashScreen()
        setContentView(R.layout.landing_page)
        supportActionBar?.hide()

        val btnGetStarted = findViewById<Button>(R.id.get_started_btn);

        btnGetStarted.setOnClickListener{
            Log.e("ButtonClick", "Button is clicked")
//            message
            Toast.makeText(this, "Note it now, remember it later!", Toast.LENGTH_SHORT).show()
//            screen transitioning
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }


        val btnSignin = findViewById<TextView>(R.id.btn_sign_in)

        btnSignin.setOnClickListener {
            Log.e("Cliable TextView button", "TextView is clicked")
            Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SigninActivity::class.java)
            startActivity((intent))
        }
    }

}