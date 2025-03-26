package com.android.noteit.managers

// Temporary user model and manager
data class User(val username: String, val email: String, val password: String)

object UserManager {
    var users = ArrayList<User>()
    var signed_in: User? = null

    fun registerUser(username: String, email: String, password: String): String {
        if (users.any { it.username == username }) {
            return "Username is already taken"
        }

        if (users.any { it.email == email }) {
            return "Email is already taken"
        }

        users.add(User(username, email, password))
        return "Registration Successful!"
    }

    fun signinUser(email: String, password: String): Boolean {
        val user = users.find { it.email == email && it.password == password }
        return if (user != null) {
            signed_in = user
            true
        } else {
            false
        }
    }

    fun signoutUser() {
        signed_in = null
    }

    fun isUserSignedIn(): Boolean {
        return signed_in != null
    }
}
