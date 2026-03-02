package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Result<Unit>> {
        return repository.sendPasswordResetEmail(email)
    }
}
