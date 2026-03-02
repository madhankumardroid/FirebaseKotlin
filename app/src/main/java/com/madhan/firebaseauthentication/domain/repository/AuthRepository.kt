package com.madhan.firebaseauthentication.domain.repository

import com.madhan.firebaseauthentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: User?
    fun login(email: String, password: String): Flow<Result<Unit>>
    fun signUp(email: String, password: String, firstName: String, lastName: String): Flow<Result<Unit>>
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit>>
    fun logout()
    fun getUserData(uid: String): Flow<Result<User>>
}
