package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.madhan.firebaseauthentication.domain.use_case.AuthUseCases
import com.madhan.firebaseauthentication.presentation.navigation.NavGraph
import com.madhan.firebaseauthentication.presentation.navigation.Screen
import com.madhan.firebaseauthentication.presentation.ui.theme.FirebaseComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authUseCases: AuthUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseComposeAppTheme {
                val navController = rememberNavController()
                val startDestination = if (authUseCases.getCurrentUser() != null) {
                    Screen.Home
                } else {
                    Screen.Login
                }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
