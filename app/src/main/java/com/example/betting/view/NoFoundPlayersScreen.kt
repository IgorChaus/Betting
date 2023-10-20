package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.NoFoundPlayersScreenBinding


class NoFoundPlayersScreen : Fragment() {

    private var _binding: NoFoundPlayersScreenBinding? = null
    private val binding: NoFoundPlayersScreenBinding
        get() = _binding ?: throw RuntimeException("NoFoundPlayersScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoFoundPlayersScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = NoFoundPlayersScreen()
    }

}