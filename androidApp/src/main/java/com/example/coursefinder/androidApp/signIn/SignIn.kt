package com.example.coursefinder.androidApp.signIn

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.shared.signIn.getAuthProviders
import com.firebase.ui.auth.AuthUI

class SignInActivity : AppCompatActivity()  {
    // register firebase ui activity
    private val firebaseAuthLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        val data = Intent()

        // set successful result and return to main activity
        setResult(Activity.RESULT_OK, data)
        finish()

    }

    override fun onResume() {
        super.onResume()

        // launch firebase ui activity with all auth providers
        firebaseAuthLauncher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(getAuthProviders())
                .build()
        )
    }
}
