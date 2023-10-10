package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.R
import com.example.betting.databinding.FavoritesScreenBinding

class FavoritesScreen : Fragment() {

    private var _binding: FavoritesScreenBinding? = null
    private val binding: FavoritesScreenBinding
        get() = _binding ?: throw RuntimeException("FavoritesScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FavoritesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchListScreen()
    }

    private fun launchListScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_list, ListScreen.getInstance())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = FavoritesScreen()
    }

}