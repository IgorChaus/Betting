package com.example.betting.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.betting.BaseFragment
import com.example.betting.databinding.SettingsScreenBinding

class SettingsScreen : BaseFragment<SettingsScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SettingsScreenBinding {
        return SettingsScreenBinding.inflate(inflater, container, attachToRoot)
    }

}