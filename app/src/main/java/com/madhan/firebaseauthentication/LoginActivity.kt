package com.madhan.firebaseauthentication

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var viewId = v?.id
        when (viewId) {
            R.id.btn_login -> {
                //TODO :"Do firebase authentication login"
            }
        }

    }

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignup: TextView
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tieEmail: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tiePassword: TextInputEditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initUI() {
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvSignup = findViewById(R.id.tv_sign_up)
        tilEmail = findViewById(R.id.til_email)
        tilPassword = findViewById(R.id.til_password)
        tieEmail = findViewById(R.id.tie_email)
        tiePassword = findViewById(R.id.til_password)
    }
}
