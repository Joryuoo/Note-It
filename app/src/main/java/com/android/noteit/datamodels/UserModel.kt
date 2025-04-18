package com.android.noteit.datamodels

import android.net.Uri
import androidx.compose.foundation.text.InlineTextContent
import com.android.noteit.R
import java.text.FieldPosition

class UserModel {
    var username = ""
    var email = ""
    var password = ""
    var profilepictureUri: Uri?= null
    var profilePicturePath: String? = null

    //Unique list of notes for individual users
    var noteList = ArrayList<NoteModel>()
    var taskList = ArrayList<TodoListModel>()

    constructor(username: String, email: String, password: String) {
        this.username = username
        this.email = email
        this.password = password
    }

    fun changeProfilePicture(imageId: Uri){
        profilepictureUri = imageId
    }

    fun updateUsername(username: String){
        this.username = username
    }

    fun updateEmail(email: String){
        this.email = email
    }

    fun updatePassword(password: String){
        this.password = password
    }

    //-------- Note functions ----------//

    fun deleteNote(note: NoteModel){
        noteList.remove(note)
    }

    fun updateNoteTitle(title: String, pos: Int){
        var note = noteList.get(pos)
        if(title.isEmpty()){
            note.updateTitle("Untitled")

            if(note.content.isEmpty()){
                deleteNote(note)
            } else{
                noteList.set(pos, note)
            }
        } else{
            note.updateTitle(title)
            noteList.set(pos, note)
        }
    }

    fun updateContent(content: String, pos: Int){
        var note = noteList.get(pos)

        if(content.isEmpty()){
            if(note.title.contentEquals("Untitled")){
                deleteNote(note)
            } else{
                note.content = ""
                noteList.set(pos, note)
            }
        } else{
            note.updateContent(content)
            noteList.set(pos, note)
        }
    }
}