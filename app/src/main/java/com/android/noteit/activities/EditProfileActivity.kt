package com.android.noteit.activities

import android.content.Context
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
    private var isProfileChanged: Boolean = false

    private val resultContract = registerForActivityResult(ActivityResultContracts.GetContent()){ result->
        if(result != null){
//            profilePicture.setImageURI(result)
//            AppManager.sessionUser?.profilepictureUri = result

            val savedUri = copyUriToInternalStorage(this, result)
            AppManager.sessionUser?.changeProfilePicture(savedUri)
            profilePicture.setImageURI(savedUri)
            isProfileChanged = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        btnEditProfilePic = findViewById(R.id.btnChangeProfile)
        profilePicture = findViewById(R.id.profilePicture)

        val oldUri: Uri? = AppManager.sessionUser?.profilepictureUri

        if (AppManager.sessionUser?.profilepictureUri == null) {
            profilePicture.setImageResource(R.drawable.profile_picture)
            Log.e("Profile", "uri is null")
        } else {
            Log.e("Profile", "Loading with glide")
//            Glide.with(this)
//                .load(AppManager.sessionUser?.profilepictureUri)
//                .placeholder(R.drawable.profile_picture)
//                .into(profilePicture)

            val uri = AppManager.sessionUser?.profilepictureUri
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.profile_picture)
                    .into(profilePicture)
            } else {
                profilePicture.setImageResource(R.drawable.profile_picture)
            }

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
            val safeUri =
                oldUri?.let { it1 ->
                    copyUriToInternalStorage(this@EditProfileActivity,
                        it1
                    )
                }

            val oldpassword = etOldPassword.text.toString().trim()
            val newpassword = etNewPassword.text.toString().trim()
            val confirmpassword = etConfirmPassword.text.toString().trim()

            if(isChangePassClicked && oldpassword.isEmpty() && newpassword.isEmpty() && confirmpassword.isEmpty() &&
                !isProfileChanged){
                finish()
                return@addCallback
            }

            if(username != AppManager.sessionUser?.username || email != AppManager.sessionUser?.email || isChangePassClicked || isProfileChanged){
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
                    AppManager.sessionUser?.changeProfilePicture(safeUri)
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
            val oldpassword = etOldPassword.text.toString().trim()
            val newpassword = etNewPassword.text.toString().trim()
            val confirmpassword = etConfirmPassword.text.toString().trim()
            val safeUri = oldUri?.let { it1 -> copyUriToInternalStorage(this, it1) }

            if(isChangePassClicked && oldpassword.isEmpty() && newpassword.isEmpty() && confirmpassword.isEmpty() &&
                !isProfileChanged){
                finish()
                return@setOnClickListener
            }

            if(username != AppManager.sessionUser?.username || email != AppManager.sessionUser?.email || isChangePassClicked || isProfileChanged){

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

                // Handle negative button click
                btnNegative.setOnClickListener {
                    myDialog.dismiss()
                }

                // Handle positive button click (confirm exit)
                btnPositive.setOnClickListener {
                    AppManager.sessionUser?.changeProfilePicture(safeUri)
                    if (!isFinishing) {
                        finish() // Ensure it's called only if the activity is not finishing
                    }
                }

                // Show dialog
                if (!isFinishing) {
                    myDialog.show()
                }
            } else {
                // If no changes, just finish the activity
                if (!isFinishing) {
                    finish()
                }
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
                }else if((AppManager.sessionUser?.username != username) && AppManager.find_username(username)){
                    Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                }else if(!isValidEmail(email)){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                } else if((AppManager.sessionUser?.email != email) && AppManager.find_email(email)){
                    Toast.makeText(this, "Email address is already taken", Toast.LENGTH_SHORT).show()
                } else{
                    AppManager.sessionUser?.username = username
                    AppManager.sessionUser?.email = email
                    Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()

                    AppManager.saveAppData(this)
                    finish()
                }
                return@setOnClickListener
            } else{

                if(AppManager.sessionUser?.username == username && AppManager.sessionUser!!.email == email &&
                    oldpassword.isEmpty() && newpassword.isEmpty() && confirmpassword.isEmpty()){
                    finish()
                    return@setOnClickListener
                }
                if(username.isEmpty() || email.isEmpty() || oldpassword.isEmpty() || newpassword.isEmpty() || confirmpassword.isEmpty()){
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else if(username.length < 3){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                }else if((AppManager.sessionUser?.username != username) && AppManager.find_username(username)){
                    Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                }else if(!isValidEmail(email)){
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                } else if((AppManager.sessionUser?.email != email) && AppManager.find_email(email)){
                    Toast.makeText(this, "Email address is already taken", Toast.LENGTH_SHORT).show()
                } else if(AppManager.sessionUser?.password != oldpassword){
                    Toast.makeText(this, "Invalid current password.", Toast.LENGTH_SHORT).show()
                } else if(newpassword != confirmpassword){
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                } else if(newpassword.length < 8 || oldpassword.length < 8){
                    Toast.makeText(this, "Passwords must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                } else if(oldpassword == newpassword){
                    Toast.makeText(this, "New password must be different from the old password.", Toast.LENGTH_SHORT).show()
                }else{
                    AppManager.sessionUser?.username = username
                    AppManager.sessionUser?.email = email
                    AppManager.sessionUser?.password = confirmpassword
                    Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()

                    AppManager.saveAppData(this)

                    finish()
                }
                return@setOnClickListener
            }
        }
    }

    fun copyUriToInternalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, "profile_picture_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
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