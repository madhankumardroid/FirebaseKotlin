package com.madhan.firebaseauthentication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()
    }

    /**
     * Initialise the firebase references
     */
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
    }

    /**
     * Activity lifecycle callback
     */
    override fun onStart() {
        super.onStart()
        getUser()
    }

    /**
     * Method to get the user information from firebase and show it in the UI
     */
    private fun getUser() {
        val user = mAuth!!.currentUser
        val userReference = mDatabaseReference!!.child(user!!.uid)

        tv_email_address_value.text = user.email
        tv_email_verified_value.text = user.isEmailVerified.toString()

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: " + databaseError.message)
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                tv_first_name_value.text = snapShot.child("firstName").value as String
                tv_last_name_value.text = snapShot.child("lastName").value as String
            }
        })
    }
}