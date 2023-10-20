package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.NoFoundFavoriteScreenBinding


class NoFoundFavoriteScreen : Fragment() {

    private var _binding: NoFoundFavoriteScreenBinding? = null
    private val binding: NoFoundFavoriteScreenBinding
        get() = _binding ?: throw RuntimeException("NoFoundFavoriteScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoFoundFavoriteScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = NoFoundFavoriteScreen()
    }

}