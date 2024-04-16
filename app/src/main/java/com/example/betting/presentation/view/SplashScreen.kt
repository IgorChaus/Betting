package com.example.betting.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.betting.common.BaseFragment
import com.example.betting.databinding.SplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseFragment<SplashScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SplashScreenBinding {
        return SplashScreenBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(
            "com.example.betting",
            AppCompatActivity.MODE_PRIVATE
        )

        val action = SplashScreenDirections.actionSplashScreenToWelcomeScreen()
        if (prefs.getBoolean(FIRST_LAUNCH, true)) {
            prefs.edit().putBoolean(FIRST_LAUNCH, false).apply()
            binding.imageView.visibility = View.VISIBLE
            binding.progressBarSplash.visibility = View.VISIBLE
            lifecycleScope.launch {
                for (i in 0 .. 90 step 30){
                    binding.progressBarSplash.progress = i
                    delay(1000)
                }
                findNavController().navigate(action)
            }
        } else {
                findNavController().navigate(action)
        }

    }

    companion object {
        private const val FIRST_LAUNCH = "first_launch"
    }

}