package com.example.betting.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.betting.Utils.BaseFragment
import com.example.betting.R
import com.example.betting.presentation.adapter.PlayerListAdapter
import com.example.betting.appComponent
import com.example.betting.databinding.ListScreenBinding
import com.example.betting.presentation.viewmodels.DiscoverViewModel
import com.example.betting.domain.models.Player
import com.example.betting.presentation.states.State
import javax.inject.Inject

class DiscoverListFragment : BaseFragment<ListScreenBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DiscoverViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    private lateinit var adapter: PlayerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PlayerListAdapter()
        adapter.itemClickListener = {
            showItem(it)
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ListScreenBinding {
        return ListScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv1.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner) {
            when(it){
                is State.Loading -> adapter.submitList(it.data)
                is State.ResultSearch -> adapter.submitList(it.data)
                is State.FilteredList -> adapter.submitList(it.data)
                is State.ContentList -> adapter.submitList(it.data)
                else -> Unit
            }
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