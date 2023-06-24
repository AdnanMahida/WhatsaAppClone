package com.ad.whatsappclone.models

data class Users(
    var userProfilePic: String? = null,
    var userName: String? = null,
    var userPassword: String? = null,
    var userEmail: String? = null,
    var userId: String? = null,
    var lastMessage: String? = null
) {
    constructor(username: String, email: String, password: String) : this(
        userName = username,
        userEmail = email,
        userPassword = password
    )
}