package com.example.betting.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.betting.utils.BaseFragment
import com.example.betting.R
import com.example.betting.databinding.WelcomeScreenBinding

class WelcomeScreen : BaseFragment<WelcomeScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): WelcomeScreenBinding {
        return WelcomeScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btStart.setOnClickListener {
            findNavController().navigate(R.id.mainScreen)
        }
    }

}