package com.example.betting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.NoInternetConnectionScreenBinding


class NoInternetConnectionScreen : Fragment() {

    private var _binding: NoInternetConnectionScreenBinding? = null
    private val binding: NoInternetConnectionScreenBinding
        get() = _binding ?: throw RuntimeException("NoInternetConnectionScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoInternetConnectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}