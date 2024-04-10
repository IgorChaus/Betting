package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.betting.BaseFragment
import com.example.betting.R
import com.example.betting.databinding.MainScreenBinding


class MainScreen : BaseFragment<MainScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): MainScreenBinding {
        return MainScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.itemIconTintList = null

        var navHostFragment = childFragmentManager.findFragmentByTag(NAV_HOST_MAIN_SCREEN)
        if (navHostFragment == null){
            navHostFragment = NavHostFragment.create(R.navigation.bottom_navigation)
            childFragmentManager.beginTransaction()
                .add(R.id.container_screen, navHostFragment, NAV_HOST_MAIN_SCREEN)
                .setPrimaryNavigationFragment(navHostFragment)
                .commitNow()
        } else {
            navHostFragment as NavHostFragment
        }

        val currentNavController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(currentNavController)

    }

    companion object {
        const val NAV_HOST_MAIN_SCREEN = "Nav Host Main Screen"
    }
}