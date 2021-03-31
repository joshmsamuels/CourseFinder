package com.example.coursefinder.androidApp

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class MainFragment: Fragment() {
    override fun onResume() {
        super.onResume()

        findNavController(this).navigate(nextDestination())
    }

    // Launches the next activity to be seen by the user. If there is an active firebase
    // user then the select screen is shown - if not then the sign in screen is shown
    private fun nextDestination(): NavDirections  {
        return FirebaseAuth.getInstance().currentUser?.let {
            return MainFragmentDirections.actionMainToSelectSearch()
        } ?: return MainFragmentDirections.actionMainToSignIn()
    }

}