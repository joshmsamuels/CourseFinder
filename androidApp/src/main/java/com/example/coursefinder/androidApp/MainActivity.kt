package com.example.coursefinder.androidApp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coursefinder.androidApp.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    private val firebaseInstance = FirebaseAuth.getInstance()

    // Fragments the show the navigation drawer instead of a back button
    private val topLevelFragments = setOf(R.id.selectSearchScreen)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseInstance.addAuthStateListener {
            if (it.currentUser?.email?.isBlank() ?: true) {
                // Hides the manage notification menu option if the user is not logged in
                binding.navigationView.navView.menu
                    .findItem(R.id.menu_manage_notifications).isVisible = false
            } else {
                // Shows the manage notifications menu when the user is logged in
                binding.navigationView.navView.menu
                    .findItem(R.id.menu_manage_notifications).isVisible = true
            }
        }
        
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""

        val appBarConfiguration = AppBarConfiguration(
            topLevelFragments,
            binding.drawerLayout
        )

        supportFragmentManager.primaryNavigationFragment?.let {
            supportFragmentManager.beginTransaction().detach(it).attach(it).commit()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //bind and set action bar
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.title = "Course Finder"
        setupActionBarWithNavController(navController)
        binding.toolbar.root.setupWithNavController(navController, appBarConfiguration)

        binding.navigationView.root.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_sign_out -> {
                    //sign user out when menu item clicked
                    userSignOut(this.applicationContext)
                    navController.navigate(MainFragmentDirections.goToMainFragment())

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menu_home -> {
                    //navigate to home fragment
                    navController.navigate(MainFragmentDirections.goToSelectSearchFragment())

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menu_manage_notifications -> {
                    //navigate to manage notifications of user
                    navController.navigate(
                        MainFragmentDirections.goToSubscribedCourseList(
                            email = email,
                            searchType = "userCourses"
                        )
                    )

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun userSignOut(context: Context) {
        AuthUI.getInstance().signOut(context)
    }

}
