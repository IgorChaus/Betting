package com.example.betting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.source.DataRepository
import com.example.betting.source.RetrofitInstance
import com.example.betting.wrappers.Response
import kotlinx.coroutines.launch

class DiscoverViewModel: ViewModel() {

    val dataRepository = DataRepository(RetrofitInstance.service)

    fun fetchPersons() {
        viewModelScope.launch {
            val listLeagues = dataRepository.getLeagues() as Response.Success
            Log.i("MyTag", listLeagues.data.toString())
        }
    }

}