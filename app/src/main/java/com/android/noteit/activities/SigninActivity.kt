package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }


        val etUsername = findViewById<EditText>(R.id.editTextUsername)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnSignup = findViewById<TextView>(R.id.btn_sign_up)
        val btnSignin = findViewById<Button>(R.id.btn_sign_in)


        btnSignin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(username.length < 3){
                Toast.makeText(this, "Username is too short", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(!AppManager.find_username(username)){
                Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(password.length < 8){
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val login = AppManager.signin(username, password, this)

            if(login){
                Toast.makeText(this, "Log in Successful", Toast.LENGTH_SHORT).show()
                Log.e("Succ", "Log in Successful")
                val intent = Intent(this, HomepageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                etUsername.text.clear()
                etPassword.text.clear()
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Oops! Looks like your username or password is incorrect.", Toast.LENGTH_SHORT).show()
                Log.e("ERROR", "wala naka log in")
                return@setOnClickListener
            }
        }

        btnSignup.setOnClickListener {
            Log.e("Clickable text", "Sign up text button is clicked")
            Toast.makeText(this, "Sign up", Toast.LENGTH_LONG).show()

            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

//    import android.util.Patterns
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}