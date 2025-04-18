package com.android.noteit.datamodels

import java.io.Serializable

class NoteModel : Serializable {
    var title = "Untitled"
    var content = ""
    var isArchived = false;

    constructor(title: String, content: String){
        this.title = title
        this.content = content
    }

    fun updateNote(newTitle: String, newContent: String){

        if(!(newTitle.isEmpty() && newContent.isEmpty())){
            if(newTitle.isEmpty()){
                title = "Untitled"
            } else{
                title = newTitle
            }

            if(newContent.isEmpty()){
                content = ""
            } else{
                content = newContent
            }
        }

    }

    fun updateContent(content: String){
        this.content = content
    }

    fun archiveNote(){
        isArchived = true
    }

    fun unArchiveNote(){
        isArchived = false
    }

    fun updateTitle(title: String){
        this.title = title
    }
}