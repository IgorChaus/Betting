package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.betting.databinding.MessageScreenBinding

class MessageScreen: Fragment() {
    private var _binding: MessageScreenBinding? = null
    private val binding: MessageScreenBinding
        get() = _binding ?: throw RuntimeException("MessageScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MessageScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMessage.text = requireArguments().getString(KEY_MESSAGE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun getInstance(message: String): Fragment {
            return MessageScreen().apply {
                arguments = Bundle().apply {
                    putString(KEY_MESSAGE, message)
                }
            }
        }

        private const val KEY_MESSAGE = "item"
    }
}