package com.example.scaler.screens.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.scaler.R
import com.example.scaler.databinding.ActivityMainBinding
import com.example.scaler.extensions.currentNavigationFragment
import com.example.scaler.extensions.removeFragmentFromBottom
import com.example.scaler.screens.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private var homeFragment: HomeFragment?=null
    private val _viewBinder by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_viewBinder.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navHostFragment?.navController?.let {
            NavigationUI.setupWithNavController(
                _viewBinder.bottomNavigationView,
                it
            )
        }

    }

    override fun onBackPressed() {
        homeFragment = supportFragmentManager.currentNavigationFragment as HomeFragment
        if(homeFragment?.videoDetailsFragment?.fullScreenMode==true)
            homeFragment?.videoDetailsFragment?.toggleFullScreen()
        else if(homeFragment?.videoDetailsFragment!=null && homeFragment?.videoDetailsFragment?.isAdded==true){
            supportFragmentManager.removeFragmentFromBottom(homeFragment?.videoDetailsFragment!!)
        } else{
            super.onBackPressed()
        }
    }

}