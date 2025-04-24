package com.android.noteit.activities

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.noteit.R
import com.android.noteit.app.AppManager
import com.android.noteit.datamodels.NoteModel

class OpenNoteActivity : AppCompatActivity() {
    private lateinit var note: NoteModel
    private lateinit var etTitle: EditText
    private lateinit var etNote: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_note)

        //        screen orientation
        val isTablet: Boolean = (resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if(isTablet){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        val btnArchive = findViewById<ImageButton>(R.id.archive)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        etTitle = findViewById(R.id.et_Title)
        etNote = findViewById(R.id.et_Content)

        val position = intent.getIntExtra("position", -1)

        note = AppManager.sessionUser?.noteList?.get(position)!!

        if(position != -1){
            if (note != null) {
                etTitle.setText(note.title)
                etNote.setText(note.content)

                if(note.isArchived){
                    btnArchive.setImageResource(R.drawable.svg_unarchive)
                } else{
                    btnArchive.setImageResource(R.drawable.baseline_archive_24)
                }

                if(note.title == "Untitled"){
                    etTitle.setText("")
                }
            }
        }

        btnBack.setOnClickListener {
            var title = etTitle.text.toString()
            val content = etNote.text.toString()
            if (note != null) {
                if(title.isEmpty()) title = "Untitled"
                if(note.title == title && note.content == content){
                    finish()
                } else{
                    note.updateNote(title, content)
                    Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
                    AppManager.saveAppData(this)
                    finish()
                }
            }
            finish()
        }

        onBackPressedDispatcher.addCallback(this){
            var title = etTitle.text.toString()
            val content = etNote.text.toString()
            if (note != null) {
                if(title.isEmpty()) title = "Untitled"
                if(note.title == title && note.content == content){
                    finish()
                } else{
                    note.updateNote(title, content)
                    Toast.makeText(this@OpenNoteActivity, "Changes saved", Toast.LENGTH_SHORT).show()
                    AppManager.saveAppData(this@OpenNoteActivity)
                    finish()
                }
            }
            finish()
        }

        btnArchive.setOnClickListener {
            if(note != null){
                if(note.isArchived){

                    val msgtitle = "Unarchive Note"
                    val msgContext = "Do you want to move this note back to your active notes?"

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
                        note.unArchiveNote()
                        Toast.makeText(this, "Note unarchived", Toast.LENGTH_SHORT).show()
                        AppManager.saveAppData(this)
                        myDialog.dismiss()
                        finish()
                    }

                    myDialog.show()
                } else{

                    val msgtitle = "Archive Note"
                    val msgContext = "Are you sure you want to archive this note? You can restore it anytime from the archive."

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
                        note.archiveNote()
                        Toast.makeText(this, "Note added to archive", Toast.LENGTH_SHORT).show()
                        AppManager.saveAppData(this)
                        myDialog.dismiss()
                        finish()
                    }

                    myDialog.show()
                }
            }
        }

        btnDelete.setOnClickListener {
            if(note != null){

                val msgtitle = "Delete Note"
                val msgContext = "Are you sure you want to delete this note? This action cannot be undone."

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
                    AppManager.sessionUser?.deleteNote(note)
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                    AppManager.saveAppData(this)
                    myDialog.dismiss()
                    finish()
                }

                myDialog.show()
            }
            return@setOnClickListener
        }
    }
}