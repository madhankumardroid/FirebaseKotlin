package com.madhan.firebaseauthentication.domain.use_case

data class AuthUseCases(
    val login: LoginUseCase,
    val signUp: SignUpUseCase,
    val logout: LogoutUseCase,
    val forgotPassword: ForgotPasswordUseCase,
    val getUserData: GetUserDataUseCase,
    val getCurrentUser: GetCurrentUserUseCase
)
