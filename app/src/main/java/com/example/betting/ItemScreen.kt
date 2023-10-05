package com.example.betting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.ItemScreenBinding

class ItemScreen : Fragment() {

    private var _binding: ItemScreenBinding? = null
    private val binding: ItemScreenBinding
        get() = _binding ?: throw RuntimeException("ItemScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ItemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

}