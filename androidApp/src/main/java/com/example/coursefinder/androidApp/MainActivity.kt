package com.example.coursefinder.androidApp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coursefinder.androidApp.course.SelectSearchFragmentDirections
import com.example.coursefinder.androidApp.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Fragments the show the navigation drawer instead of a back button
    private val topLevelFragments = setOf(R.id.selectSearchScreen, R.id.searchViewScreen)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBarConfiguration = AppBarConfiguration(
            topLevelFragments,
            binding.drawerLayout
        )

        supportFragmentManager.primaryNavigationFragment?.let {
            supportFragmentManager.beginTransaction().detach(it).attach(it).commit()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar.root)
        setupActionBarWithNavController(navController)
        binding.toolbar.root.setupWithNavController(navController, appBarConfiguration)

        binding.navigationView.root.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_sign_out -> {
                    userSignOut(this.applicationContext)
                    navController.navigate(MainFragmentDirections.goToMainFragment())
                    true
                }
                R.id.menu_home-> {
                    navController.navigate(MainFragmentDirections.goToSelectSearchFragment())
                    true
                }
                R.id.menu_manage_notifications->{
                    navController.navigate(MainFragmentDirections.goToSubscribedCourseList("userCourses"))
//
                    true
                }
                else->{
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
