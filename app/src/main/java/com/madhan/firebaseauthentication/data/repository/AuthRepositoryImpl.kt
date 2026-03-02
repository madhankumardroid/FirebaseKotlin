package com.madhan.firebaseauthentication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.madhan.firebaseauthentication.data.di.IoDispatcher
import com.madhan.firebaseauthentication.domain.model.User
import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override val currentUser: User?
        get() = firebaseAuth.currentUser?.let {
            User(uid = it.uid, email = it.email ?: "", isEmailVerified = it.isEmailVerified)
        }

    override fun login(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            trySend(Result.success(Unit))
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
    }.flowOn(ioDispatcher)

    override fun signUp(email: String, password: String, firstName: String, lastName: String): Flow<Result<Unit>> = callbackFlow {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid
            if (uid != null) {
                val userMap = mapOf("firstName" to firstName, "lastName" to lastName)
                firebaseDatabase.reference.child("Users").child(uid).setValue(userMap).await()
                firebaseAuth.currentUser?.sendEmailVerification()?.await()
                trySend(Result.success(Unit))
            } else {
                trySend(Result.failure(Exception("User UID is null")))
            }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
    }.flowOn(ioDispatcher)

    override fun sendPasswordResetEmail(email: String): Flow<Result<Unit>> = callbackFlow {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            trySend(Result.success(Unit))
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
    }.flowOn(ioDispatcher)

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getUserData(uid: String): Flow<Result<User>> = callbackFlow {
        try {
            val snapshot = firebaseDatabase.reference.child("Users").child(uid).get().await()
            val firstName = snapshot.child("firstName").value as? String ?: ""
            val lastName = snapshot.child("lastName").value as? String ?: ""
            val user = User(
                uid = uid,
                firstName = firstName,
                lastName = lastName,
                email = firebaseAuth.currentUser?.email ?: "",
                isEmailVerified = firebaseAuth.currentUser?.isEmailVerified ?: false
            )
            trySend(Result.success(user))
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
    }.flowOn(ioDispatcher)
}
