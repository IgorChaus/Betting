package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.betting.R
import com.example.betting.adapter.PlayerListAdapter
import com.example.betting.databinding.ListScreenBinding
import com.example.betting.viewmodel.FavoriteViewModel
import com.example.betting.wrappers.PlayerAdapterItem
import com.example.betting.wrappers.State

class FavoritesListFragment : Fragment() {

    private var _binding: ListScreenBinding? = null
    private val binding: ListScreenBinding
        get() = _binding ?: throw RuntimeException("ListScreenBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
    }

    private lateinit var adapter: PlayerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PlayerListAdapter()
        adapter.itemClickListener = {
            showItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv1.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.ResultSearch -> adapter.submitList(it.data)
                is State.FilteredList -> adapter.submitList(it.data)
                is State.ContentList -> adapter.submitList(it.data)
                else -> Unit
            }
        }
    }

    private fun showItem(item: PlayerAdapterItem){
        val args = Bundle().apply {
            putParcelable(PlayerScreen.KEY_ITEM, item)
        }
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.container_activity) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.playerScreen, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}