package com.ad.whatsappclone.models

import android.Manifest

object Constraints {
    const val USER_NODE = "Users"
    const val CHATS_NODE = "Chats"
    const val PERMISSION_REQUEST_CODE = 100
    val REQUIRED_PERMISSION = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    const val USER_ID = "userId"
    const val USER_PROFILE_PIC = "userProfilePic"
    const val USER_NAME = "userName"
}