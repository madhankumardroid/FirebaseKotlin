package com.madhan.firebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    //Global variables
    private var email: String? = null
    private var password: String? = null

    //UI elements
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignup: TextView
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tieEmail: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tiePassword: TextInputEditText
    private lateinit var loginProgress: ProgressBar

    //Firebase reference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
        initListeners()
        mAuth = FirebaseAuth.getInstance()
    }

    /**
     * Initialise the UI elements
     */
    private fun initUI() {
        btnLogin = findViewById(R.id.btn_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvSignup = findViewById(R.id.tv_sign_up)
        tilEmail = findViewById(R.id.til_email)
        tilPassword = findViewById(R.id.til_password)
        tieEmail = findViewById(R.id.tie_email)
        tiePassword = findViewById(R.id.tie_password)
        loginProgress = findViewById(R.id.login_progress)
    }

    /**
     * Method to set listeners for view events like click
     */
    private fun initListeners() {
        tvSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            //TODO: Need to implement
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    /**
     * Call the firebase method to login the user to the app with the entered email and password
     */
    private fun loginUser() {
        if (checkNetworkConnection()) {
            email = tieEmail.text.toString()
            password = tiePassword.text.toString()
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
        btnLogin.isEnabled = enableView
        btnLogin.alpha = if (enableView) 1f else 0.5f
        loginProgress.visibility = if (enableView) View.GONE else View.VISIBLE
    }

    /**
     * After successful login, go to main screen
     */
    private fun gotoMainActivity() {
        navigate<MainActivity>()
    }
}