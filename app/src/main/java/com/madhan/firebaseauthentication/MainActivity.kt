package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private lateinit var btnLogout: Button
    private lateinit var tvFirstNameValue: TextView
    private lateinit var tvLastNameValue: TextView
    private lateinit var tvEmailAddressValue: TextView
    private lateinit var tvEmailVerifiedValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()
    }

    /**
     * Initialise the firebase references
     */
    private fun initialise() {
        btnLogout = findViewById(R.id.btn_logout)
        tvFirstNameValue = findViewById(R.id.tv_first_name_value)
        tvLastNameValue = findViewById(R.id.tv_last_name_value)
        tvEmailAddressValue = findViewById(R.id.tv_email_address_value)
        tvEmailVerifiedValue = findViewById(R.id.tv_email_verified_value)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnLogout.setOnClickListener {
            mAuth?.signOut()
            finish()
        }
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
        val user = mAuth!!.currentUser ?: return
        val userReference = mDatabaseReference!!.child(user.uid)

        tvEmailAddressValue.text = user.email
        tvEmailVerifiedValue.text = user.isEmailVerified.toString()

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: " + databaseError.message)
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                tvFirstNameValue.text = snapShot.child("firstName").value as? String ?: ""
                tvLastNameValue.text = snapShot.child("lastName").value as? String ?: ""
            }
        })
    }
}