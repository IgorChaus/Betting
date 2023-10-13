package com.example.betting.wrappers

sealed class State {

    class Content(val data: List<AdapterItems>) : State()

    class Error(val errorMessage: String) : State()

    // object Loading: State()

    // object NothingFound: State()
}
