package com.example.coursefinder.androidApp

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.androidApp.course.SelectActivity
import com.example.coursefinder.androidApp.signIn.SignInActivity
import com.example.coursefinder.shared.user.USER_ANON_FIELD
import com.example.coursefinder.shared.user.USER_EMAIL_FIELD
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val startSignInActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            Log.d(
                "main activity",
                "sign in activity result code: ${result.resultCode} --- result: $result"
            )

            TODO("result was not ok")
        }

        launchNextActivity()

    }

    private val startSelectActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            Log.d("main activity", "sign in activity result: ${result.resultCode}")
            TODO("result was not ok")
        }

        launchNextActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launchNextActivity()
    }

    // Launches the next activity to be seen by the user. If there is an active firebase
    // user then the select screen is shown - if not then the sign in screen is shown
    private fun launchNextActivity() {
        FirebaseAuth.getInstance().currentUser?.let {
            val selectIntent = Intent(this, SelectActivity::class.java)
            selectIntent.putExtra(USER_EMAIL_FIELD, it.email)
            selectIntent.putExtra(USER_ANON_FIELD, it.isAnonymous)
            startSelectActivity.launch(selectIntent)
        } ?: startSignInActivity.launch(Intent(this, SignInActivity::class.java))
    }
}
