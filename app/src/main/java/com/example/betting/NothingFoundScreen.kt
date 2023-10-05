package com.example.betting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.NothingFoundScreenBinding


class NothingFoundScreen : Fragment() {

    private var _binding: NothingFoundScreenBinding? = null
    private val binding: NothingFoundScreenBinding
        get() = _binding ?: throw RuntimeException("NothingFoundScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NothingFoundScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

}