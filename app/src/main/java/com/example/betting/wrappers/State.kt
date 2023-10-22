package com.example.betting.wrappers

sealed class State {
    class ContentList(val data: List<AdapterItems>) : State()

    class FilteredList(val data: List<AdapterItems>) : State()

    class ResultSearch(val data: List<AdapterItems>) : State()

    object Error : State()

    object NothingFound: State()
}
