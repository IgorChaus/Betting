package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.betting.R
import com.example.betting.adapter.PlayerListAdapter
import com.example.betting.databinding.ListScreenBinding
import com.example.betting.viewmodel.DiscoverViewModel
import com.example.betting.wrappers.PlayerItemAdapter
import com.example.betting.wrappers.State

class DiscoverListFragment : Fragment() {

    private var _binding: ListScreenBinding? = null
    private val binding: ListScreenBinding
        get() = _binding ?: throw RuntimeException("ListScreenBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[DiscoverViewModel::class.java]
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
            if (it is State.Content) {
                adapter.submitList(it.data)
            }
        }
    }

    private fun showItem(item: PlayerItemAdapter){
        val mainScreen = requireActivity()
            .supportFragmentManager
            .findFragmentByTag(MainScreen.MAIN_SCREEN)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container_activity, ItemScreen.getInstance(item))
            .apply {
                mainScreen?.let {
                    hide(it)
                }
            }
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = DiscoverListFragment()
    }

}