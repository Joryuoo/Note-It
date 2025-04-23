package com.android.noteit.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//email validation import
import android.util.Patterns
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.managers.UserManager

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page)


        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            var requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            var requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }


        val etUsername = findViewById<EditText>(R.id.editTextUsername)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val btnSignup = findViewById<Button>(R.id.btn_sign_up)
        val btnSignin = findViewById<TextView>(R.id.btn_sign_in)

        btnSignup.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirm = etConfirmPassword.text.toString().trim()

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(username.length < 3){
                Toast.makeText(this, "Username is too short", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(AppManager.find_username(username)){
                Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!isValidEmail(email)){
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(AppManager.find_email(email)){
                Toast.makeText(this, "Email is already taken", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(password.length < 8){
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(!password.equals(confirm)){
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                AppManager.registerUser(username, email, confirm, this)
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                Log.e("SigninActivity", "Registration Successful")
                etUsername.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()
                etConfirmPassword.text.clear()

                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
            }
        }

        btnSignin.setOnClickListener {
            Log.e("SigninActivity", "TextView is clicked : Signin")
            Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SigninActivity::class.java)
            startActivity((intent))
        }
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}