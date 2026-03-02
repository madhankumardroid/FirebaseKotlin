package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String, firstName: String, lastName: String): Flow<Result<Unit>> {
        return repository.signUp(email, password, firstName, lastName)
    }
}
