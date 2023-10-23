package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.NoFavoritePlayersScreenBinding


class NoFavoritePlayersScreen : Fragment() {

    private var _binding: NoFavoritePlayersScreenBinding? = null
    private val binding: NoFavoritePlayersScreenBinding
        get() = _binding ?: throw RuntimeException("NoFavoritePlayersScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoFavoritePlayersScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = NoFavoritePlayersScreen()
    }

}