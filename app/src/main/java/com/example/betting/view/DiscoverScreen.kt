package com.example.betting.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.betting.BettingApp
import com.example.betting.R
import com.example.betting.databinding.DiscoverScreenBinding
import com.example.betting.viewmodel.DiscoverViewModel
import com.example.betting.viewmodel.DiscoverViewModelFactory
import com.example.betting.wrappers.State
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
        binding.tvSearchResult.visibility = View.GONE
        setupObserverProgressBar()
        setupObserverCancelImage()
        setupListenersOnSearch()
        if(savedInstanceState == null){
            launchListScreen()
        }
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is State.Content -> {
                    launchListScreen()
                }
                is State.NothingFound -> {
                    launchNothingFoundScreen()
                }
                is State.Error -> {
                    launchNoInternetConnectionScreen()
                }
            }
        }
    }

    private fun setupListenersOnSearch() {
        binding.search.addTextChangedListener { s ->
            viewModel.searchPlayers(s.toString())
        }

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                if (binding.search.text.toString().isEmpty()) {
                    binding.tvSearchResult.visibility = View.GONE
                    viewModel.updatePlayersList()
                } else {
                    binding.tvSearchResult.visibility = View.VISIBLE
                }
                deacivateSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.showCancelIcon()
                binding.layoutSearch.background = ResourcesCompat
                    .getDrawable(resources, R.drawable.round_corners_border, null)
                binding.tvSearchResult.visibility = View.GONE
            } else {
                binding.layoutSearch.background = ResourcesCompat
                    .getDrawable(resources, R.drawable.round_corners, null)
            }

        }
    }

    private fun setupObserverCancelImage() {
        viewModel.cancelSearch.observe(viewLifecycleOwner) {
            if (it) {
                binding.ivCancelSearch.setImageResource(R.drawable.icon_cancel_24px)
                binding.ivCancelSearch.setOnClickListener {
                    binding.tvSearchResult.visibility = View.GONE
                    val editableText = Editable.Factory.getInstance().newEditable("")
                    binding.search.text = editableText
                    deacivateSearch()
                    viewModel.showSearchIcon()
                    viewModel.updatePlayersList()
                }
            } else {
                binding.ivCancelSearch.setImageResource(R.drawable.icon_search_24px)
                binding.ivCancelSearch.setOnClickListener(null)
            }
        }
    }

    private fun deacivateSearch(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(binding.search.windowToken, 0)
        binding.layoutSearch.background = ResourcesCompat
            .getDrawable(resources, R.drawable.round_corners, null)
        binding.search.setFocusable(false)
        binding.search.setFocusableInTouchMode(true)
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
        childFragmentManager.beginTransaction()
            .replace(R.id.container_list, DiscoverListFragment.getInstance())
            .commit()
    }

    private fun launchNothingFoundScreen() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container_list, NoFoundPlayersScreen.getInstance())
            .commit()
    }

    private fun launchNoInternetConnectionScreen() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container_list, NoInternetConnectionScreen.getInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = DiscoverScreen()
    }

}