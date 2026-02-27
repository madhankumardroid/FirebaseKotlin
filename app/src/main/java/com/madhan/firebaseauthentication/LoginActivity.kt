package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.madhan.firebaseauthentication.databinding.ActivityLoginBinding

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding

    //Global variables
    private var email: String? = null
    private var password: String? = null

    //Firebase reference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initListeners()
        mAuth = FirebaseAuth.getInstance()
    }

    /**
     * Method to set listeners for view events like click
     */
    private fun initListeners() {
        binding.tvSignUp.setOnClickListener {
            navigateWithBack<SignUpActivity>()
        }

        binding.tvForgotPassword.setOnClickListener {
            navigateWithBack<ForgotPasswordActivity>()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    /**
     * Call the firebase method to login the user to the app with the entered email and password
     */
    private fun loginUser() {
        if (checkNetworkConnection()) {
            email = binding.tieEmail.text.toString()
            password = binding.tiePassword.text.toString()
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                toggleState(false)

                mAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
                    toggleState(true)
                    if (it.isSuccessful) {
                        Log.d(TAG, "Login successful")
                        gotoMainActivity()
                    } else {
                        Log.e(TAG, "Login failure", it.exception)
                        toast(getString(R.string.login_failed) + " " + it.exception!!.message)
                    }
                }
            } else {
                toast(getString(R.string.fields_mandatory))
            }
        } else {
            toast(getString(R.string.check_network_connection))
        }
    }


    /**
     * Toggle the state of the login button. Also determine the visibility of progress bar
     */
    private fun toggleState(enableView: Boolean) {
        binding.btnLogin.isEnabled = enableView
        binding.btnLogin.alpha = if (enableView) 1f else 0.5f
        binding.loginProgress.visibility = if (enableView) View.GONE else View.VISIBLE
    }

    /**
     * After successful login, go to main screen
     */
    private fun gotoMainActivity() {
        navigate<MainActivity>()
    }
}