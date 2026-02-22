package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etForgotEmail: EditText
    private lateinit var btnSendEmail: Button
    private lateinit var progressForgotPassword: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        
        etForgotEmail = findViewById(R.id.et_forgot_email)
        btnSendEmail = findViewById(R.id.btn_send_email)
        progressForgotPassword = findViewById(R.id.progress_forgot_password)

        btnSendEmail.setOnClickListener { sendPasswordResetLink() }
    }

    /**
     * Method to call the firebase api to send the forgot password link to mail id given
     */
    private fun sendPasswordResetLink() {
        progressForgotPassword.visibility = View.VISIBLE
        btnSendEmail.isEnabled = false
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val email = etForgotEmail.text
        if (TextUtils.isEmpty(email)) {
            toast(getString(R.string.enter_email))
            progressForgotPassword.visibility = View.GONE
            btnSendEmail.isEnabled = true
        } else {
            firebaseAuth.sendPasswordResetEmail(email.toString()).addOnCompleteListener {
                progressForgotPassword.visibility = View.GONE
                btnSendEmail.isEnabled = true
                if (it.isSuccessful) {
                    toast(getString(R.string.email_sent))
                    finish()
                } else {
                    toast(it.exception?.message ?: "An error occurred")
                }
            }
        }
    }
}
