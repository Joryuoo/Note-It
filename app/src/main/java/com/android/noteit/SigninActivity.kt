package com.android.noteit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)

        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnSignup = findViewById<TextView>(R.id.btn_sign_up)
        val btnSignin = findViewById<Button>(R.id.btn_sign_in)


        btnSignin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(!isValidEmail(email)){
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(password.length < 8){
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val login = UserManager.signinUser(email, password);

            if(login){
                Toast.makeText(this, "Naka log in", Toast.LENGTH_SHORT).show()
                Log.e("Succ", "Log in Successful")
                val intent = Intent(this, HomepageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //clear the edit text
                etEmail.text.clear()
                etPassword.text.clear()
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Oops! Looks like your email or password is incorrect.", Toast.LENGTH_SHORT).show()
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

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}