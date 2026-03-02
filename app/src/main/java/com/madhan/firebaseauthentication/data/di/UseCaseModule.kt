package com.madhan.firebaseauthentication.data.di

import com.madhan.firebaseauthentication.domain.repository.AuthRepository
import com.madhan.firebaseauthentication.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            login = LoginUseCase(repository),
            signUp = SignUpUseCase(repository),
            logout = LogoutUseCase(repository),
            forgotPassword = ForgotPasswordUseCase(repository),
            getUserData = GetUserDataUseCase(repository),
            getCurrentUser = GetCurrentUserUseCase(repository)
        )
    }
}
