package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.model.User
import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): User? {
        return repository.currentUser
    }
}
