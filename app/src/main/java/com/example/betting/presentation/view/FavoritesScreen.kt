package com.example.betting.presentation.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.betting.R
import com.example.betting.common.BaseFragment
import com.example.betting.common.hideKeyboard
import com.example.betting.appComponent
import com.example.betting.databinding.FavoritesScreenBinding
import com.example.betting.domain.models.Player
import com.example.betting.presentation.adapter.PlayerListAdapter
import com.example.betting.presentation.states.State
import com.example.betting.presentation.viewmodels.FavoriteViewModel
import com.example.betting.common.repeatOnCreated
import javax.inject.Inject

class FavoritesScreen : BaseFragment<FavoritesScreenBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }

    private val adapter by lazy { PlayerListAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FavoritesScreenBinding {
        return FavoritesScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListenersOnSearch()
        setupStateObserver()

        adapter.itemClickListener = {
            showItem(it)
        }
        binding.rv.adapter = adapter

        viewModel.getFavoritePlayers()
    }

    private fun setupStateObserver() {
        viewModel.state.repeatOnCreated(this) {
            when (it) {
                is State.ContentList -> {
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_search_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    binding.rv.visibility = View.VISIBLE
                    binding.tvMessage.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is State.ActivateSearch -> {
                    binding.layoutSearch.background = ResourcesCompat
                        .getDrawable(resources, R.drawable.round_corners_border, null)
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                }
                is State.ResultSearch -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    adapter.submitList(it.data)
                }
                is State.FilteredList -> {
                    adapter.submitList(it.data)
                }
                is State.NothingFound -> {
                    binding.tvSearchResult.visibility = View.VISIBLE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_cancel_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    binding.rv.visibility = View.GONE
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = getString(R.string.cant_find_player)
                }
                is State.NoFavoritePlayers -> {
                    binding.tvSearchResult.visibility = View.GONE
                    binding.ivCloseSearch.setImageResource(R.drawable.icon_search_24px)
                    requireContext().hideKeyboard(binding.search)
                    deactivateSearch()
                    binding.rv.visibility = View.GONE
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = getString(R.string.no_favorite_player)
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
            viewModel.getFavoritePlayers()
        }
    }

    private fun showItem(item: Player){

        val args = Bundle().apply {
            putParcelable(PlayerScreen.KEY_ITEM, item)
        }
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.container_activity) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.playerScreen, args)
    }

}