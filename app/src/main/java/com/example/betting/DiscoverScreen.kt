package com.example.betting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.DiscoverScreenBinding

class DiscoverScreen : Fragment() {

    private var _binding: DiscoverScreenBinding? = null
    private val binding: DiscoverScreenBinding
        get() = _binding ?: throw RuntimeException("DiscoverScreenBinding == null")

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
        launchListScreen()

    }

    private fun launchListScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_list, ListScreen.getInstance())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = DiscoverScreen()
    }

}