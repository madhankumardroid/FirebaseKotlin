package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}
