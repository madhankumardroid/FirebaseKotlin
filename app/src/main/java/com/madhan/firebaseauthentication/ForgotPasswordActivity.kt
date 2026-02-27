package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.madhan.firebaseauthentication.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendEmail.setOnClickListener { sendPasswordResetLink() }
    }

    /**
     * Method to call the firebase api to send the forgot password link to mail id given
     */
    private fun sendPasswordResetLink() {
        binding.progressForgotPassword.visibility = View.VISIBLE
        binding.btnSendEmail.isEnabled = false
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val email = binding.etForgotEmail.text
        if (TextUtils.isEmpty(email)) {
            toast(getString(R.string.enter_email))
            binding.progressForgotPassword.visibility = View.GONE
            binding.btnSendEmail.isEnabled = true
        } else {
            firebaseAuth.sendPasswordResetEmail(email.toString()).addOnCompleteListener {
                binding.progressForgotPassword.visibility = View.GONE
                binding.btnSendEmail.isEnabled = true
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
