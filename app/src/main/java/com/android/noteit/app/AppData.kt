package com.android.noteit.app

import com.android.noteit.datamodels.UserModel

data class AppData(
    val sessionUser: UserModel?,
    val registeredUsers: ArrayList<UserModel>
)