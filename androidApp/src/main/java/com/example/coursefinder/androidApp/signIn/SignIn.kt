package com.example.coursefinder.androidApp.signIn

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.shared.signIn.getAuthProviders
import com.firebase.ui.auth.AuthUI

class SignInActivity : AppCompatActivity()  {
    private val firebaseAuthLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            TODO("Sign In Failed")
        }

        val data = Intent()
        setResult(Activity.RESULT_OK, data)
        finish()

    }

    override fun onResume() {
        super.onResume()

        firebaseAuthLauncher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(getAuthProviders())
                .build()
        )
    }
}
