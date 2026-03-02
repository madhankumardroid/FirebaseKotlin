package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Result<Unit>> {
        return repository.login(email, password)
    }
}
