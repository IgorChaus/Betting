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
        if (childFragmentManager.findFragmentByTag(DISCOVER_SCREEN) == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.container_screen, DiscoverScreen.getInstance(), DISCOVER_SCREEN)
                .commit()
        }else {
            childFragmentManager.beginTransaction()
                .show(DiscoverScreen.getInstance())
                .commit()
        }
    }

    private fun launchFavoritesScreen(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_screen, FavoritesScreen.getInstance())
            .commit()
    }

    private fun launchSettingsScreen(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_screen, SettingsScreen.getInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = MainScreen()
        const val DISCOVER_SCREEN = "DiscoverScreen"
    }
}