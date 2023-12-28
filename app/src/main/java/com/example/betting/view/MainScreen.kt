package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.betting.R
import com.example.betting.databinding.MainScreenBinding


class MainScreen : Fragment() {

    private var _binding: MainScreenBinding? = null
    private val binding: MainScreenBinding
        get() = _binding ?: throw RuntimeException("MainScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.itemIconTintList = null

        var navHostFragment = childFragmentManager.findFragmentByTag(NAV_HOST_MAIN_SCREEN)
        if (navHostFragment == null){
            navHostFragment = NavHostFragment.create(R.navigation.bottom_navigation)
            childFragmentManager.beginTransaction()
                .add(R.id.container_screen, navHostFragment, NAV_HOST_MAIN_SCREEN)
                .commitNow()
        } else {
            navHostFragment as NavHostFragment
        }
        binding.bottomNav.setupWithNavController(navHostFragment.navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance() = MainScreen()
        const val MAIN_SCREEN = "MainScreen"
        const val NAV_HOST_MAIN_SCREEN = "Nav Host Main Screen"
    }
}