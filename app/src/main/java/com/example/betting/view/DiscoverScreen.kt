package com.example.betting.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.betting.BettingApp
import com.example.betting.R
import com.example.betting.databinding.DiscoverScreenBinding
import com.example.betting.viewmodel.DiscoverViewModelFactory
import com.example.betting.viewmodel.DiscoverViewModel
import javax.inject.Inject

class DiscoverScreen : Fragment() {

    private var _binding: DiscoverScreenBinding? = null
    private val binding: DiscoverScreenBinding
        get() = _binding ?: throw RuntimeException("DiscoverScreenBinding == null")

    val component by lazy{
        (requireActivity().application as BettingApp).component
    }

    @Inject
    lateinit var factory: DiscoverViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[DiscoverViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        setupObserverProgressBar()
        launchListScreen()
    }

    private fun setupObserverProgressBar() {
        viewModel.progressBar.observe(requireActivity()) {
            binding.progressBarDiscover.progress = it
        }
        viewModel.progressBarVisible.observe(requireActivity()) {
            binding.progressBarDiscover.visibility = it
        }
    }

    private fun launchListScreen() {
        if (childFragmentManager.findFragmentByTag(DISLOVER_LIST_SCREEN) == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.container_list, DiscoverListFragment.getInstance(), DISLOVER_LIST_SCREEN)
                .commit()
        }else {
            childFragmentManager.beginTransaction()
                .show(DiscoverListFragment.getInstance())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = DiscoverScreen()
        const val DISLOVER_LIST_SCREEN = "DiscoverListScreen"
    }

}