package com.example.betting.wrappers

import com.example.betting.model.LeaguesResponse

sealed class Response {
    class Success<T>(val data: List<T>) : Response()

    class Error(val errorMessage: String) : Response()
}