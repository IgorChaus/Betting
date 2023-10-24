package com.example.betting.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
        setupListenersOnSearch()
        setupStateObserver()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getPlayersFromAllLeagues()
        }
        if(savedInstanceState == null){
            launchListScreen()
        }
    }

    private fun setupStateObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            val isListScreenNotInContainer = childFragmentManager
                .findFragmentByTag(LIST_SCREEN_FRAGMENT) == null
            Log.i("MyTag", "State $it")
            when (it) {
                is State.Loading -> {
                    binding.progressBarDiscover.progress = it.progress
                    binding.progressBarDiscover.visibility = it.progressVisible
                    if (isListScreenNotInContainer){
                        launchListScreen()
                    }
                }
                is State.ContentList -> {
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_search_24px)
                    hideKeyboard()
                    deactivateSearch()
                    if (isListScreenNotInContainer) {
                        launchListScreen()
                    }
                }
                is State.ActivateSearch -> {
                    binding.layoutSearch.background = ResourcesCompat
                        .getDrawable(resources, R.drawable.round_corners_border, null)
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                }
                is State.ResultSearch -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    hideKeyboard()
                    deactivateSearch()
                    if (isListScreenNotInContainer) {
                        launchListScreen()
                    }
                }
                is State.NothingFound -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    hideKeyboard()
                    deactivateSearch()
                    launchNothingFoundMessage()
                }
                is State.Error -> {
                    launchNoInternetConnectionMessage()
                }
                else -> Unit
            }
        }
    }

    private fun deactivateSearch() {
        binding.layoutSearch.background = ResourcesCompat
            .getDrawable(resources, R.drawable.round_corners, null)
        binding.search.isFocusable = false
        binding.search.isFocusableInTouchMode = true
    }

    private fun hideKeyboard(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(binding.search.windowToken, 0)
    }

    private fun setupListenersOnSearch() {
        binding.search.addTextChangedListener { s ->
            viewModel.setFilteredListState(s.toString())
        }

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                viewModel.setSearchResultState(binding.search.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        binding.search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.setActivateSearch()
            }
        }

        binding.ivCloseSearch.setOnClickListener {
            val editableText = Editable.Factory.getInstance().newEditable("")
            binding.search.text = editableText
            viewModel.setContentListState()
        }
    }

    private fun launchListScreen() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container_list, DiscoverListFragment.getInstance(), LIST_SCREEN_FRAGMENT)
            .commit()
    }

    private fun launchNothingFoundMessage() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.container_list, MessageScreen.getInstance(
                    "We can't find this player. Check the spelling or try another name"
                )
            )
            .commit()
    }

    private fun launchNoInternetConnectionMessage() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.container_list, MessageScreen.getInstance(
                    "No internet connection is found.\nPull to refresh."
                )
            )
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = DiscoverScreen()
        const val LIST_SCREEN_FRAGMENT = "List Screen Fragment"
    }

}