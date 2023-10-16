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
import com.example.betting.databinding.FavoritesScreenBinding
import com.example.betting.viewmodel.DiscoverViewModel
import com.example.betting.viewmodel.DiscoverViewModelFactory
import com.example.betting.viewmodel.FavoriteViewModel
import com.example.betting.viewmodel.FavoriteViewModelFactory
//import com.example.betting.viewmodel.FavoriteViewModel
//import com.example.betting.viewmodel.FavoriteViewModelFactory
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
        launchListScreen()
    }

    private fun launchListScreen() {
        if (childFragmentManager.findFragmentByTag(FAVORITE_LIST_SCREEN) == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.container_list, FavoritesListFragment.getInstance(), FAVORITE_LIST_SCREEN)
                .commit()
        }else {
            childFragmentManager.beginTransaction()
                .show(FavoritesListFragment.getInstance())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = FavoritesScreen()
        const val FAVORITE_LIST_SCREEN = "FavoriteListScreen"
    }

}