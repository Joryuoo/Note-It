package com.android.noteit.activities

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.datamodels.TodoListModel

class OpenTaskActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_ID = "com.android.noteit.activities.OpenTaskActivity.EXTRA_TASK_ID"
    }

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var cbMarkAsDone: CheckBox
    private lateinit var btnBack: ImageButton
    private lateinit var btnDelete: ImageButton
    private lateinit var btnArchive: ImageButton
    private lateinit var task: TodoListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_task)


        etTaskTitle = findViewById(R.id.et_Title)
        etTaskDescription = findViewById(R.id.et_Content)
        cbMarkAsDone = findViewById(R.id.markAsDone)
        btnBack = findViewById(R.id.btnBack)
        btnDelete = findViewById(R.id.btnCancel)
        btnArchive = findViewById(R.id.archive)


        val taskId = intent.getStringExtra(EXTRA_TASK_ID)

        if (taskId != null) {
            Log.d("OpenTaskActivity", "Received Task ID: $taskId")
            task = AppManager.getTaskById(taskId)!!
            if (task != null) {
                displayTaskDetails(task)
                cbMarkAsDone.setOnCheckedChangeListener { _, isChecked ->
                    val success = AppManager.updateTaskCompletionStatus(taskId, isChecked)
                    if (!success) {
                        Toast.makeText(this, "Failed to update status", Toast.LENGTH_SHORT).show()
                        cbMarkAsDone.isChecked = !isChecked
                    } else {
                        updateTitleAppearance(isChecked)
                        AppManager.saveAppData(this)
                    }
                }

                btnBack.setOnClickListener {
                    val title = etTaskTitle.text.toString().trim()
                    var desc = etTaskDescription.text.toString().trim()
                    val done = cbMarkAsDone.isChecked

                    if(desc.isEmpty()) desc = ""

                    if(title.isNotEmpty()){
                        task.updateTask(title, desc, done)
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                        AppManager.saveAppData(this)
                    }
                    finish()
                }

                onBackPressedDispatcher.addCallback(this){
                    val title = etTaskTitle.text.toString().trim()
                    var desc = etTaskDescription.text.toString().trim()
                    val done = cbMarkAsDone.isChecked

                    if(desc.isEmpty()) desc = ""

                    if(title.isNotEmpty()){
                        task.updateTask(title, desc, done)
                        Toast.makeText(this@OpenTaskActivity, "Saved", Toast.LENGTH_SHORT).show()
                        AppManager.saveAppData(this@OpenTaskActivity)
                    }
                    finish()
                }


                btnDelete.setOnClickListener {

                    val msgtitle = "Delete Task"
                    val msgContext = "Are you sure you want to delete this task? This action cannot be undone."

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
                        AppManager.sessionUser?.taskList?.remove(task)
                        Log.d("OpenTaskActivity", "Delete button clicked for task ID: $taskId")
                        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                        AppManager.saveAppData(this)
                        finish()
                    }

                    myDialog.show()
                }

                btnArchive.setOnClickListener {
                    Log.e("Archive", "Clicked")
                    if(task.isArchived){
                        val msgtitle = "Unarchive Task"
                        val msgContext = "Do you want to move this task back to your active list?"

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
                            task.unArchiveTask()
                            Log.d("OpenTaskActivity", "Archive button clicked for task ID: $taskId")
                            Toast.makeText(this, "Task  unarchived", Toast.LENGTH_SHORT).show()
                            AppManager.saveAppData(this)
                            finish()
                        }

                        myDialog.show()

                    } else{
                        val msgtitle = "Archive Task"
                        val msgContext = "Are you sure you want to archive this task? You can restore it anytime from the archive."

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
                            task.addToArchive()
                            Log.d("OpenTaskActivity", "Archive button clicked for task ID: $taskId")
                            Toast.makeText(this, "Task  added to archive", Toast.LENGTH_SHORT).show()
                            AppManager.saveAppData(this)
                            finish()
                        }
                        myDialog.show()

                    }
                }
            } else {
                Log.e("OpenTaskActivity", "Task with ID '$taskId' not found.")
                Toast.makeText(this, "Error: Task not found", Toast.LENGTH_LONG).show()
                finish()
            }
        } else {

            Log.e("OpenTaskActivity", "Error: No Task ID provided in Intent extras.")
            Toast.makeText(this, "Error loading task details", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun displayTaskDetails(task: TodoListModel) {
        etTaskTitle.setText(task.title)
        etTaskDescription.setText(task.description)
        cbMarkAsDone.isChecked = task.isDone
        updateTitleAppearance(task.isDone)
    }

    private fun updateTitleAppearance(isDone: Boolean) {
        if (isDone) {
            etTaskTitle.paintFlags = etTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            etTaskTitle.paintFlags = etTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onPause() {
        super.onPause()

        val title = etTaskTitle.text.toString().trim()
        var desc = etTaskDescription.text.toString().trim()
        val done = cbMarkAsDone.isChecked

        if (desc.isEmpty()) desc = ""

        if (title.isNotEmpty()) {
            task.updateTask(title, desc, done)
            Toast.makeText(this@OpenTaskActivity, "Saved", Toast.LENGTH_SHORT).show()
            AppManager.saveAppData(this@OpenTaskActivity)
        }

        AppManager.saveAppData(this)
    }
}