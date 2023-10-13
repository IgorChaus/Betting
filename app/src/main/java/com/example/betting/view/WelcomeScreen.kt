package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.R
import com.example.betting.databinding.WelcomeScreenBinding

class WelcomeScreen : Fragment() {

    private var _binding: WelcomeScreenBinding? = null
    private val binding: WelcomeScreenBinding
        get() = _binding ?: throw RuntimeException("WelcomeScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = WelcomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btStart.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity, MainScreen.getInstance())
                .addToBackStack("MainScreen")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = WelcomeScreen()
    }

}