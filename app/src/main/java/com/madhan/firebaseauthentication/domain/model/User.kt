package com.madhan.firebaseauthentication.domain.model

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isEmailVerified: Boolean = false
)
