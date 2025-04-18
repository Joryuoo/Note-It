package com.android.noteit.datamodels

import java.util.UUID


class TodoListModel {
    val id: String = UUID.randomUUID().toString()
    var title: String = "Untitled"
    var isDone: Boolean = false
    var description: String = ""
    var isArchived: Boolean = false

    constructor(title: String, description: String){
        this.title = title
        this.description = description
    }

    fun updateTitle(title: String){
        this.title = title
    }

    fun updateTask(newTitle: String, newDescription: String, isdone: Boolean){
        if(!title.isEmpty()){
            title = newTitle
            description = newDescription
            isDone = isdone
        }
    }

    fun toogleCheckBox(){
        if (isDone){
            isDone = false
        } else{
            isDone = true
        }
    }

    fun addToArchive(){
        isArchived = true
    }

    fun unArchiveTask(){
        isArchived = false
    }


}