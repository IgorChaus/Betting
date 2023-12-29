package com.example.betting.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.betting.BettingApp
import com.example.betting.R
import com.example.betting.databinding.FavoritesScreenBinding
import com.example.betting.viewmodel.FavoriteViewModel
import com.example.betting.viewmodel.FavoriteViewModelFactory
import com.example.betting.wrappers.State
import com.example.betting.wrappers.hideKeyboard
import javax.inject.Inject

class FavoritesScreen : Fragment() {

    private var _binding: FavoritesScreenBinding? = null
    private val binding: FavoritesScreenBinding
        get() = _binding ?: throw RuntimeException("FavoritesScreenBinding == null")

    val component by lazy{
        (requireActivity().application as BettingApp).component
    }

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[FavoriteViewModel::class.java]
    }

    private var currentNavHostController: NavController? = null

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FavoritesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListenersOnSearch()
        setupStateObserver()

        var navHostFragment = childFragmentManager.findFragmentByTag(NAV_HOST_LIST_SCREEN)
        if (navHostFragment == null){
            navHostFragment = NavHostFragment.create(R.navigation.favorite_list_screen_navigation)
            childFragmentManager.beginTransaction()
                .add(R.id.container_list, navHostFragment, DiscoverScreen.NAV_HOST_LIST_SCREEN)
                .commitNow()
        } else {
            navHostFragment as NavHostFragment
        }

        currentNavHostController = navHostFragment.navController
        currentNavHostController?.navigate(R.id.favoritesListFragment)

    }

    private fun setupStateObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            val isListScreenNotInContainer = childFragmentManager
                .findFragmentByTag(DiscoverScreen.LIST_SCREEN_FRAGMENT) == null
            when (it) {
                is State.ContentList -> {
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_search_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    if (isListScreenNotInContainer) {
                        currentNavHostController?.navigate(R.id.favoritesListFragment)
                    }
                }
                is State.ActivateSearch -> {
                    binding.layoutSearch.background = ResourcesCompat
                        .getDrawable(resources, R.drawable.round_corners_border, null)
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    if (isListScreenNotInContainer) {
                        currentNavHostController?.navigate(R.id.favoritesListFragment)
                    }
                }

                is State.ResultSearch -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    if (isListScreenNotInContainer) {
                        currentNavHostController?.navigate(R.id.favoritesListFragment)
                    }
                }
                is State.NothingFound -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    launchNothingFoundMessage()
                }
                is State.NoFavoritePlayers -> {
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_search_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    launchNoFavoritePlayersMessage()
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

    private fun launchNothingFoundMessage() {
        val args = Bundle().apply {
            putString(MessageScreen.KEY_MESSAGE, getString(R.string.cant_find_player))
        }
        currentNavHostController?.navigate(R.id.messageScreen,args)
    }

    private fun launchNoFavoritePlayersMessage() {
        val args = Bundle().apply {
            putString(MessageScreen.KEY_MESSAGE, getString(R.string.no_favorite_player))
        }
        currentNavHostController?.navigate(R.id.messageScreen,args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val NAV_HOST_LIST_SCREEN = "Nav Host List Screen"
    }

}