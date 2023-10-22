package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.betting.databinding.NoInternetConnectionScreenBinding
import com.example.betting.viewmodel.DiscoverViewModel


class NoInternetConnectionScreen : Fragment() {

    private var _binding: NoInternetConnectionScreenBinding? = null
    private val binding: NoInternetConnectionScreenBinding
        get() = _binding ?: throw RuntimeException("NoInternetConnectionScreenBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[DiscoverViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NoInternetConnectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getPlayersFromAllLeagues()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = NoInternetConnectionScreen()
    }

}