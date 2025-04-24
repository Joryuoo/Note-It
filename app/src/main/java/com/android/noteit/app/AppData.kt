package com.android.noteit.app

import com.android.noteit.datamodels.UserModel
//used to store data to GSON
data class AppData(
    val sessionUser: UserModel?,
    val registeredUsers: ArrayList<UserModel>
)