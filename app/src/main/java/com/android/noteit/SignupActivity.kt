package com.android.noteit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//email validation import
import android.util.Patterns

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page)

        val etUsername = findViewById<EditText>(R.id.editTextUsername)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnSignup = findViewById<Button>(R.id.btn_sign_up)
        val btnSignin = findViewById<TextView>(R.id.btn_sign_in)

        btnSignup.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val intent = Intent(this, SigninActivity::class.java)

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(!isValidEmail(email)){
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(password.length < 8){
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val message = UserManager.registerUser(username, email, password)

            if(message == "Registration Successful!"){
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                Log.e("Succ", "Registration Successful")
                etUsername.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()

                startActivity(intent)
            } else{
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                Log.e("ERROR", message)

            }
        }

        btnSignin.setOnClickListener {
            Log.e("Cliable TextView button", "TextView is clicked")
            Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SigninActivity::class.java)
            startActivity((intent))
        }
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}