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

    val discoverScreen = DiscoverScreen.getInstance()
    val favoritesScreen = FavoritesScreen.getInstance()
    val settingsScreen = SettingsScreen.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.setItemIconTintList(null)
        setupBottomNavigation(view)
        launchDiscoverScreen()
    }

    private fun setupBottomNavigation(view: View) {
        val bottomNav: BottomNavigationView = view.findViewById(R.id.bottomNav)
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

    private fun launchDiscoverScreen(){
        val fragmentTransaction  = childFragmentManager.beginTransaction()
        if(discoverScreen.isAdded) {
            fragmentTransaction.show(discoverScreen)
        }else{
            fragmentTransaction.add(R.id.container_screen, discoverScreen)
        }
        if (favoritesScreen.isAdded) {
            fragmentTransaction.hide(favoritesScreen)
        }
        if (settingsScreen.isAdded) {
            fragmentTransaction.hide(settingsScreen)
        }
        fragmentTransaction.commit()
    }

    private fun launchFavoritesScreen(){
        val fragmentTransaction  = childFragmentManager.beginTransaction()
        if(favoritesScreen.isAdded) {
            fragmentTransaction.show(favoritesScreen)
        }else{
            fragmentTransaction.add(R.id.container_screen, favoritesScreen)
        }
        if (discoverScreen.isAdded) {
            fragmentTransaction.hide(discoverScreen)
        }
        if (settingsScreen.isAdded) {
            fragmentTransaction.hide(settingsScreen)
        }
        fragmentTransaction.commit()
    }

    private fun launchSettingsScreen(){
        val fragmentTransaction  = childFragmentManager.beginTransaction()
        if(settingsScreen.isAdded) {
            fragmentTransaction.show(settingsScreen)
        }else{
            fragmentTransaction.add(R.id.container_screen, settingsScreen)
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

    companion object{
        fun getInstance() = MainScreen()
    }
}