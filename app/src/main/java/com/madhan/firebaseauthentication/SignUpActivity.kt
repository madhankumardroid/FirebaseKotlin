package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    //Constants
    private val TAG = "SignUpActivity"

    //Firebase references
    private var mDatabaseRef: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private lateinit var btnSignUpAccount: Button
    private lateinit var progressSignUp: ProgressBar
    private lateinit var tieFirstName: TextInputEditText
    private lateinit var tieLastName: TextInputEditText
    private lateinit var tieSignupEmail: TextInputEditText
    private lateinit var tieSignupPassword: TextInputEditText

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
        btnSignUpAccount = findViewById(R.id.btn_sign_up_account)
        progressSignUp = findViewById(R.id.progress_signup)
        tieFirstName = findViewById(R.id.tie_first_name)
        tieLastName = findViewById(R.id.tie_last_name)
        tieSignupEmail = findViewById(R.id.tie_signup_email)
        tieSignupPassword = findViewById(R.id.tie_signup_password)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseRef = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnSignUpAccount.setOnClickListener { createAccount() }
    }

    /**
     * Initialise the global variables, check values and do account creation
     */
    private fun createAccount() {
        firstName = tieFirstName.text.toString()
        lastName = tieLastName.text.toString()
        emailAddress = tieSignupEmail.text.toString()
        password = tieSignupPassword.text.toString()

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
        btnSignUpAccount.visibility = View.GONE
        progressSignUp.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(emailAddress!!, password!!).addOnCompleteListener(this) {
            progressSignUp.visibility = View.GONE

            if (it.isSuccessful) {
                Log.d(TAG, "Account created successfully")
                verifyEmail()
                updateUserInDatabase(mAuth!!.currentUser)
            } else {
                Log.e(TAG, "Account creation failed: " + it.exception?.message)
                toast(getString(R.string.account_creation_failed) + it.exception?.message)
                btnSignUpAccount.visibility = View.VISIBLE
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