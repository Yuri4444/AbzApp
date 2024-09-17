package com.yuri_berezhnyi.abzapp.ui.core

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.collectNavigationTarget {
                        it.invoke(navController)
                    }
                }
            }
        }

        with(binding) {
            llUsers.setOnClickListener {
                navController.navigate(R.id.usersFragment)
                ivUsers.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.blue), PorterDuff.Mode.SRC_IN)
                ivSignUp.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.black), PorterDuff.Mode.SRC_IN)
                tvUsers.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
                tvSignUp.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
            }
            llSignUp.setOnClickListener {
                navController.navigate(R.id.signUpFragment)
                ivUsers.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.black), PorterDuff.Mode.SRC_IN)
                ivSignUp.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.blue), PorterDuff.Mode.SRC_IN)
                tvUsers.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                tvSignUp.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.splashFragment)
        navController.setGraph(navGraph, null)
        navController.addOnDestinationChangedListener(listener)
    }

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->

        when (destination.id) {
            R.id.splashFragment -> handleScreen()
            R.id.noInternetFragment -> handleScreen()
            R.id.usersFragment -> handleScreen(isVisibleBottomBar = true)
            R.id.signUpFragment -> handleScreen(isVisibleBottomBar = true)
            R.id.successFragment -> handleScreen()
            R.id.failureFragment -> handleScreen()

            else -> handleScreen()
        }
    }

    private fun handleScreen(
        isVisibleBottomBar: Boolean = false
    ) {
        binding.llBottomBar.isVisible = isVisibleBottomBar
    }
}