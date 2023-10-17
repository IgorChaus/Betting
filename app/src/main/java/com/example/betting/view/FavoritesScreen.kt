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
import com.example.betting.viewmodel.FavoriteViewModel
import com.example.betting.viewmodel.FavoriteViewModelFactory
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
        val viewModel = ViewModelProvider(requireActivity(), factory)[FavoriteViewModel::class.java]
        launchListScreen()
    }

    private fun launchListScreen() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container_list, FavoritesListFragment.getInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = FavoritesScreen()
    }

}