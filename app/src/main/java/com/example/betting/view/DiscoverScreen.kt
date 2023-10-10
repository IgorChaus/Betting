package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.betting.R
import com.example.betting.databinding.DiscoverScreenBinding
import com.example.betting.viewmodel.DiscoverViewModel

class DiscoverScreen : Fragment() {

    private var _binding: DiscoverScreenBinding? = null
    private val binding: DiscoverScreenBinding
        get() = _binding ?: throw RuntimeException("DiscoverScreenBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[DiscoverViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DiscoverScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchListScreen()
        viewModel.fetchPersons()

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
        fun getInstance() = DiscoverScreen()
    }

}