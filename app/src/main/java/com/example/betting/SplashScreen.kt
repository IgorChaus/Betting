package com.example.betting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        coroutineScope.launch {
            for (i in 0 .. 90 step 30){
                binding.progressBar.progress = i
                delay(1000)
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity, WelcomeScreen.getInstance())
                .commit()
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


    companion object{
        fun getInstance() = SplashScreen()
    }

}