package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.R
import com.example.betting.databinding.MainScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


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

    lateinit var  bottomNav: BottomNavigationView

    lateinit var discoverScreen: Fragment
    lateinit var favoritesScreen: Fragment
    lateinit var settingsScreen: Fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentsInstans()
        binding.bottomNav.setItemIconTintList(null)
        setupBottomNavigation(view)
        if (savedInstanceState == null){
            launchDiscoverScreen()
        }
    }

    private fun getFragmentsInstans() {
        discoverScreen = childFragmentManager.findFragmentByTag(DISCOVER_SCREEN)
            ?: DiscoverScreen.getInstance()
        favoritesScreen = childFragmentManager.findFragmentByTag(FAVORITES_SCREEN)
            ?: FavoritesScreen.getInstance()
        settingsScreen = childFragmentManager.findFragmentByTag(SETTINGS_SCREEN)
            ?: SettingsScreen.getInstance()
    }

    private fun setupBottomNavigation(view: View) {
        bottomNav = view.findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.discover -> {
                    launchDiscoverScreen()
                    true
                }
                R.id.favorites -> {
                    launchFavoritesScreen()
                    true
                }
                R.id.settings -> {
                    launchSettingsScreen()
                    true
                }
                else -> throw RuntimeException("Illegal choose")
            }
        }
    }

    private fun launchDiscoverScreen() {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        if (discoverScreen.isAdded) {
            fragmentTransaction.show(discoverScreen)
        } else {
            fragmentTransaction.add(R.id.container_screen, discoverScreen, DISCOVER_SCREEN)
        }

        if (favoritesScreen.isAdded) {
            fragmentTransaction.hide(favoritesScreen)
        }

        if (settingsScreen.isAdded) {
            fragmentTransaction.hide(settingsScreen)
        }

        fragmentTransaction.commit()
    }

    private fun launchFavoritesScreen() {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        if (favoritesScreen.isAdded) {
            fragmentTransaction.show(favoritesScreen)
        } else {
            fragmentTransaction.add(R.id.container_screen, favoritesScreen, FAVORITES_SCREEN)
        }

        if (discoverScreen.isAdded) {
            fragmentTransaction.hide(discoverScreen)
        }

        if (settingsScreen.isAdded) {
            fragmentTransaction.hide(settingsScreen)
        }

        fragmentTransaction.commit()
    }

    private fun launchSettingsScreen() {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        if (settingsScreen.isAdded) {
            fragmentTransaction.show(settingsScreen)
        } else {
            fragmentTransaction.add(R.id.container_screen, settingsScreen, SETTINGS_SCREEN)
        }

        if (discoverScreen.isAdded) {
            fragmentTransaction.hide(discoverScreen)
        }

        if (favoritesScreen.isAdded) {
            fragmentTransaction.hide(favoritesScreen)
        }

        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance() = MainScreen()
        const val MAIN_SCREEN = "MainScreen"
        const val DISCOVER_SCREEN = "DiscoverScreen"
        const val FAVORITES_SCREEN = "FavoritesScreen"
        const val SETTINGS_SCREEN = "SettingsScreen"
    }
}