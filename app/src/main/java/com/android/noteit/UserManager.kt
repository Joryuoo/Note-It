package com.android.noteit

import android.widget.Toast

// this file is only temporary
data class User(val username: String, val email: String, val password: String )
object UserManager {
    var users = ArrayList<User>();

    fun registerUser(username : String, email : String, password: String): String{
        if(users.any{it.username == username}){
            return "Username is already taken"
        }

        if(users.any{it.email == email}){
            return "Email is already taken"
        }

//        HAHAHAHAHAHAH JK XDXDXD
//        if(users.any{it.password == password}){
//            return "Password is already taken"
//        }

        users.add(User(username, email, password))
        return "Registration Successful!"
    }

    fun signinUser(email: String, password: String): Boolean{
        if(users.any{it.email == email} && users.any{it.password == password}) {
            return true
        }
        return false
    }
}