package com.madhan.firebaseauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.madhan.firebaseauthentication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialise()
    }

    /**
     * Initialise the firebase references
     */
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
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

        binding.tvEmailAddressValue.text = user.email
        binding.tvEmailVerifiedValue.text = user.isEmailVerified.toString()

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: " + databaseError.message)
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                binding.tvFirstNameValue.text = snapShot.child("firstName").value as? String ?: ""
                binding.tvLastNameValue.text = snapShot.child("lastName").value as? String ?: ""
            }
        })
    }
}