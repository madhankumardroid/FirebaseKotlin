package com.madhan.firebaseauthentication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    //Constants
    private val TAG = "SignUpActivity"

    //Firebase references
    private var mDatabaseRef: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //Global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var emailAddress: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initialise()
    }

    /**
     * Initialise the Firebase references and set click listener for the sign up button
     */
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseRef = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btn_sign_up_account.setOnClickListener { createAccount() }
    }

    /**
     * Initialise the global variables, check values and do account creation
     */
    private fun createAccount() {
        firstName = tie_first_name.text.toString()
        lastName = tie_last_name.text.toString()
        emailAddress = tie_signup_email.text.toString()
        password = tie_signup_password.text.toString()

        if (TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName)
                && TextUtils.isEmpty(emailAddress) && TextUtils.isEmpty(password)) {
            toast(getString(R.string.fields_mandatory))
        } else {
            doAccountCreation()
        }
    }

    /**
     * Call the appropriate Firebase method to create the user with email and password
     */
    private fun doAccountCreation() {
        btn_sign_up_account.visibility = View.GONE
        progress_signup.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(emailAddress!!, password!!).addOnCompleteListener(this) {
            progress_signup.visibility = View.GONE

            if (it.isSuccessful) {
                Log.d(TAG, "Account created successfully")
                verifyEmail()
                updateUserInDatabase(mAuth!!.currentUser)
            }
        }
    }

    /**
     * Call the appropriate Firebase method to send email verification
     */
    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this) {
            if (it.isSuccessful) {
                toast(getString(R.string.verification_email_sent) + mUser.email)
            } else {
                toast(getString(R.string.verification_email_failure))
            }
        }
    }

    /**
     * Update the user values in Firebase Realtime Database
     */
    private fun updateUserInDatabase(currentUser: FirebaseUser?) {
        val currentUserInDb = mDatabaseRef!!.child(currentUser!!.uid)
        currentUserInDb.child("firstName").setValue(firstName)
        currentUserInDb.child("lastName").setValue(lastName)
        gotoMainActivity()
    }

    /**
     * After successful account creation, go to main screen
     */
    private fun gotoMainActivity() {
        navigate<MainActivity>()
    }
}