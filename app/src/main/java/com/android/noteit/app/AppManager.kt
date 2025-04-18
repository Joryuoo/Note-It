package com.android.noteit.app

import android.util.Log
import com.android.noteit.datamodels.NoteModel
import com.android.noteit.datamodels.TodoListModel
import com.android.noteit.datamodels.UserModel

object AppManager {
    //Users
    var sessionUser: UserModel ?= null
    var registeredUsers = ArrayList<UserModel>()


    //Userfunctions
    fun find_username(username: String): Boolean{
        return registeredUsers.any{it.username == username}
    }

    fun find_email(email: String): Boolean{
        return registeredUsers.any{it.email == email}
    }


    fun registerUser(username: String, email:String, password: String){
        var newUser: UserModel = UserModel(username, email, password)
        newUser.noteList.add(
            NoteModel(
                "Welcome to Note It!",
                "Start organizing your thoughts, ideas, and tasks all in one place. Tap the + button to create your first note!"
            )
        )
        registeredUsers.add(newUser)
    }

    fun signin(username: String, password: String): Boolean{
        if(registeredUsers.any{it.username == username && it.password == password}){
            sessionUser = registeredUsers.find { it.username == username && it.password == password }
            return true
        }
        return false
    }

    fun updateSessionUser(newUsername: String, newEmail: String, newPassword: String): String{
        if(sessionUser?.username != newUsername){
            if(find_username(newUsername)){
                return "Username is already taken!"
            }
        }

        if(sessionUser?.email != newEmail){
            if(find_email(newEmail)){
                return "Email is already taken!"
            }
        }
        //para safe lang
        if(!find_username(newUsername) && !find_email(newEmail)){
            val pos: Int = registeredUsers.indexOf(sessionUser)
            sessionUser?.username = newUsername
            sessionUser?.email = newEmail
            sessionUser?.password = newPassword
            //para safe lang sad
            var updatedUser: UserModel? = sessionUser

            if (updatedUser != null) {
                registeredUsers.set(pos, updatedUser)
                sessionUser = updatedUser //trust issues malala
            }
            return "User updated successfully!"
        }
        return  "ambot"
    }

    fun getTaskById(taskId: String): TodoListModel? {
        return sessionUser?.taskList?.find { it.id == taskId }
    }

    fun signout(){
        sessionUser = null;
    }

    fun updateTaskCompletionStatus(taskId: String, isChecked: Boolean): Boolean {
        // Get the current user, return false if no user is logged in
        val user = sessionUser
        if (user == null) {
            Log.e("AppManager", "updateTaskCompletionStatus failed: No user logged in.")
            return false
        }

        // Find the task within the user's task list using the provided ID
        // Assumes UserModel has 'var taskList: ArrayList<TodoListModel>'
        val taskToUpdate = user.taskList.find { it.id == taskId }

        return if (taskToUpdate != null) {
            // Task found, update its isDone status
            taskToUpdate.isDone = isChecked
            Log.d("AppManager", "Task '${taskToUpdate.title}' (ID: $taskId) updated. isDone = $isChecked")
            true // Indicate success
        } else {
            // Task with the given ID was not found for this user
            Log.w("AppManager", "updateTaskCompletionStatus failed: Task with ID '$taskId' not found for user '${user.username}'.")
            false // Indicate failure
        }
    }
    
}