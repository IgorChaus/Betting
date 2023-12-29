package com.example.betting.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.betting.R
import com.example.betting.databinding.SplashScreenBinding
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    private var _binding: SplashScreenBinding? = null
    private val binding: SplashScreenBinding
        get() = _binding ?: throw RuntimeException("SplashScreenBinding == null")

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(
            "com.example.betting",
            AppCompatActivity.MODE_PRIVATE
        )
        if (prefs.getBoolean(FIRST_LAUNCH, true)) {
            prefs.edit().putBoolean(FIRST_LAUNCH, false).apply()
            binding.imageView.visibility = View.VISIBLE
            binding.progressBarSplash.visibility = View.VISIBLE
            coroutineScope.launch {
                for (i in 0 .. 90 step 30){
                    binding.progressBarSplash.progress = i
                    delay(1000)
                }
                findNavController().navigate(R.id.action_splashScreen_to_welcomeScreen)
            }
        } else {
                findNavController().navigate(R.id.action_splashScreen_to_welcomeScreen)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }


    companion object {
        private const val FIRST_LAUNCH = "first_launch"
    }

}