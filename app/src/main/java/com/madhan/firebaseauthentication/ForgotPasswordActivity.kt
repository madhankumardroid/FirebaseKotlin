package com.madhan.firebaseauthentication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        btn_send_email.setOnClickListener { sendPasswordResetLink() }
    }

    /**
     * Method to call the firebase api to send the forgot password link to mail id given
     */
    private fun sendPasswordResetLink() {
        progress_forgot_password.visibility = View.VISIBLE
        btn_send_email.isEnabled = false
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val email = et_forgot_email.text
        if (TextUtils.isEmpty(email)) {
            toast(getString(R.string.enter_email))
        } else {
            firebaseAuth.sendPasswordResetEmail(email.toString()).addOnCompleteListener {
                progress_forgot_password.visibility = View.GONE
                btn_send_email.isEnabled = true
                if (it.isSuccessful) {
                    toast(getString(R.string.email_sent))
                    finish()
                } else {
                    toast(it.exception!!.message!!)
                }
            }
        }
    }
}
