package com.example.betting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.betting.BaseFragment
import com.example.betting.databinding.MessageScreenBinding

class MessageScreen: BaseFragment<MessageScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): MessageScreenBinding {
        return MessageScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMessage.text = requireArguments().getString(KEY_MESSAGE)
    }

    companion object {
        const val KEY_MESSAGE = "item"
    }
}