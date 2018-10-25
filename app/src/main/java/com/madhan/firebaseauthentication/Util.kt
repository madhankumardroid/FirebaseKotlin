package com.madhan.firebaseauthentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

/**
 * Method for screen navigation
 */
inline fun <reified T : Activity> Activity.navigate() {
    val intent = Intent(this, T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

/**
 * To check network connection availability
 */
fun Activity.checkNetworkConnection(): Boolean {
    try {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork!!.type == ConnectivityManager.TYPE_WIFI || activeNetwork.type == ConnectivityManager.TYPE_MOBILE
    } catch (e: Exception) {
        Log.e("Util", "checkNetworkConnection: Exception: " + e.localizedMessage)
    }
    return false
}

/**
 * To show toast message
 */
fun Activity.toast(toastMessage: String) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}