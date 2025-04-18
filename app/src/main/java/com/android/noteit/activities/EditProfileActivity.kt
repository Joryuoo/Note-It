package com.android.noteit.activities

import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream


class EditProfileActivity : AppCompatActivity() {
    private lateinit var profilePicture: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var btnEditProfilePic: ImageButton

    private val resultContract = registerForActivityResult(ActivityResultContracts.GetContent()){ result->
        if(result != null){
            profilePicture.setImageURI(result)
            AppManager.sessionUser?.profilepictureUri = result
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        btnEditProfilePic = findViewById(R.id.btnChangeProfile)
        profilePicture = findViewById(R.id.profilePicture)

        val oldUri = AppManager.sessionUser?.profilepictureUri

        if (AppManager.sessionUser?.profilepictureUri == null) {
            profilePicture.setImageResource(R.drawable.profile_picture)
            Log.e("Profile", "uri is null")
        } else {
            Log.e("Profile", "Loading with glide")
            Glide.with(this)
                .load(AppManager.sessionUser?.profilepictureUri)
                .placeholder(R.drawable.profile_picture)
                .into(profilePicture)
        }

        val etUsername = findViewById<EditText>(R.id.editTextUsername)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etOldPassword = findViewById<EditText>(R.id.editTextOldPassword)
        val etNewPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val btnSaveChanges = findViewById<Button>(R.id.btnSaveChanges)
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        val btnBack = findViewById<ImageButton>(R.id.btnBack_to_main)
        var isChangePassClicked = false

        disableEditText(etOldPassword)
        disableEditText(etNewPassword)
        disableEditText(etConfirmPassword)

        etUsername.setText(AppManager.sessionUser?.username ?: "")
        etEmail.setText(AppManager.sessionUser?.email ?: "")


        btnEditProfilePic.setOnClickListener {
            resultContract.launch("image/*")
        }

        onBackPressedDispatcher.addCallback(this){
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if(username != AppManager.sessionUser?.username || email != AppManager.sessionUser?.email || isChangePassClicked){
                val msgtitle = "Unsaved Changes"
                val msgContext = "Are you sure you want to go back without saving?"

                val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_box, null)
                val myDialog = android.app.Dialog(this@EditProfileActivity)

                myDialog.setCancelable(true)
                myDialog.setContentView(dialogBinding)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val title = dialogBinding.findViewById<TextView>(R.id.dialogTitle)
                val context = dialogBinding.findViewById<TextView>(R.id.dialogContext)
                val btnPositive = dialogBinding.findViewById<Button>(R.id.btnPositive)
                val btnNegative = dialogBinding.findViewById<Button>(R.id.btnNegatve)

                title.setText(msgtitle)
                context.setText(msgContext)

                btnNegative.setOnClickListener {
                    myDialog.dismiss()
                }

                btnPositive.setOnClickListener {
                    AppManager.sessionUser?.profilepictureUri = oldUri
                    finish()
                }

                myDialog.show()
            } else{
                finish()
            }
            return@addCallback
        }

        btnBack.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if(username != AppManager.sessionUser?.username || email != AppManager.sessionUser?.email || isChangePassClicked){
                val msgtitle = "Unsaved Changes"
                val msgContext = "Are you sure you want to go back without saving?"

                val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_box, null)
                val myDialog = android.app.Dialog(this)

                myDialog.setCancelable(true)
                myDialog.setContentView(dialogBinding)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val title = dialogBinding.findViewById<TextView>(R.id.dialogTitle)
                val context = dialogBinding.findViewById<TextView>(R.id.dialogContext)
                val btnPositive = dialogBinding.findViewById<Button>(R.id.btnPositive)
                val btnNegative = dialogBinding.findViewById<Button>(R.id.btnNegatve)

                title.setText(msgtitle)
                context.setText(msgContext)

                btnNegative.setOnClickListener {
                    myDialog.dismiss()
                }

                btnPositive.setOnClickListener {
                    AppManager.sessionUser?.profilepictureUri = oldUri
                    finish()
                }

                myDialog.show()
            } else{
                finish()
                return@setOnClickListener
            }
        }

        btnChangePassword.setOnClickListener {
            enableEditText(etOldPassword)
            enableEditText(etNewPassword)
            enableEditText(etConfirmPassword)
            isChangePassClicked = true

            Toast.makeText(this, "You can now change your password", Toast.LENGTH_SHORT).show()
        }

        btnSaveChanges.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val oldpassword = etOldPassword.text.toString().trim()
            val newpassword = etNewPassword.text.toString().trim()
            val confirmpassword = etConfirmPassword.text.toString().trim()

            if(!isChangePassClicked){
                if(username == AppManager.sessionUser?.username && email == AppManager.sessionUser?.email){
                    finish()
                    return@setOnClickListener
                }
            }

            if(!isChangePassClicked){
                if(username.isEmpty() || email.isEmpty()){
                    if(username.isEmpty() && !email.isEmpty()){
                        Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                    } else if(!username.isEmpty() && email.isEmpty()){
                        Toast.makeText(this, "Email address cannot be empty", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "Username and email address cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                    return@setOnClickListener
                } else if(username.length < 3){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else if((AppManager.sessionUser?.username != username) && AppManager.find_username(username)){
                    Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else if(!isValidEmail(email)){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if((AppManager.sessionUser?.email != email) && AppManager.find_email(email)){
                    Toast.makeText(this, "Email address is already taken", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else{
                    AppManager.sessionUser?.username = username
                    AppManager.sessionUser?.email = email
                    Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                    finish()
                    return@setOnClickListener
                }
            } else{
                if(username.isEmpty() || email.isEmpty() || oldpassword.isEmpty() || newpassword.isEmpty() || confirmpassword.isEmpty()){
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if(username.length < 3){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else if((AppManager.sessionUser?.username != username) && AppManager.find_username(username)){
                    Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else if(!isValidEmail(email)){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if((AppManager.sessionUser?.email != email) && AppManager.find_email(email)){
                    Toast.makeText(this, "Email address is already taken", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if(AppManager.sessionUser?.password != oldpassword){
                    Toast.makeText(this, "Invalid current password.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if(newpassword != confirmpassword){
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else{
                    AppManager.sessionUser?.username = username
                    AppManager.sessionUser?.email = email
                    AppManager.sessionUser?.password = confirmpassword
                    Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                    finish()
                    return@setOnClickListener
                }
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun disableEditText(editText: EditText){
        editText.isEnabled = false
    }

    fun enableEditText(editText: EditText){
        editText.isEnabled = true
    }
}