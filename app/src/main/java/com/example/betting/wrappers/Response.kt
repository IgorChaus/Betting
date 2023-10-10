package com.example.betting.wrappers

import com.example.betting.model.LeaguesResponse

sealed class Response {
    class Success(val data: List<LeaguesResponse.LeagueItem>) : Response()

    class Error(val errorMessage: String) : Response()
}