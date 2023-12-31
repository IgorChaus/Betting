package com.example.betting.wrappers

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()

    data class Error(val errorMessage: String) : Response<Nothing>()
}