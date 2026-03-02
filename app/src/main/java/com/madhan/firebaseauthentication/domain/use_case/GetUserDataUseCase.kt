package com.madhan.firebaseauthentication.domain.use_case

import com.madhan.firebaseauthentication.domain.model.User
import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(uid: String): Flow<Result<User>> {
        return repository.getUserData(uid)
    }
}
