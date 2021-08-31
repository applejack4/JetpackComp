package com.example.jetpackcomp.View.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jetpackcomp.R
import com.example.jetpackcomp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var Mbinding :ActivityMainBinding
    private lateinit var mNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Mbinding.root)

        mNavController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_favorite_dish, R.id.navigation_random_dish
        ))
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        Mbinding.navView.setupWithNavController(mNavController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, null)
    }

    fun hideBottomNavigation(){
        Mbinding.navView.clearAnimation()
        Mbinding.navView.animate().translationY(Mbinding.navView.height.toFloat()).duration = 300
        Mbinding.navView.visibility = View.GONE
    }

    fun showBottomNavigation(){
        Mbinding.navView.clearAnimation()
        Mbinding.navView.animate().translationY(0f).duration = 300
        Mbinding.navView.visibility = View.INVISIBLE
    }
}