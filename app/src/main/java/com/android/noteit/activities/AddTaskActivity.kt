package com.android.noteit.activities

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.datamodels.TodoListModel

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val tvTitle = findViewById<EditText>(R.id.et_Title)
        val tvDescription = findViewById<EditText>(R.id.et_Desc)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            val title = tvTitle.text.toString().trim()
            var desc = tvDescription.text.toString().trim()

            if(title.isEmpty() && desc.isEmpty()){
                finish()
                return@setOnClickListener
            }

            if(desc.isEmpty()) desc = ""

            if(title.isNotEmpty()){
                AppManager.sessionUser?.taskList?.add(0, TodoListModel(title, desc))
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                AppManager.saveAppData(this)
                finish()
            } else{
                Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        onBackPressedDispatcher.addCallback(this){
            val title = tvTitle.text.toString().trim()
            var desc = tvDescription.text.toString().trim()

            if(title.isEmpty() && desc.isEmpty()){
                finish()
                return@addCallback
            }

            if(desc.isEmpty()) desc = ""

            if(title.isNotEmpty()){
                AppManager.sessionUser?.taskList?.add(0, TodoListModel(title, desc))
                Toast.makeText(this@AddTaskActivity, "Saved", Toast.LENGTH_SHORT).show()
                AppManager.saveAppData(this@AddTaskActivity)
                finish()
            } else{
                Toast.makeText(this@AddTaskActivity, "Task title cannot be empty", Toast.LENGTH_SHORT).show()
                return@addCallback
            }
        }

        val btnCancel = findViewById<ImageButton>(R.id.btnCancel)

        btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        AppManager.saveAppData(this)  // Save data when the activity is paused
    }
}