package com.example.betting.wrappers

import com.example.betting.domain.models.AdapterItems

sealed class State {
    class Loading(
        val data: List<AdapterItems>,
        val progress: Int,
        val progressVisible: Int
    ) : State()

    class ContentList(val data: List<AdapterItems>) : State()

    object ActivateSearch: State()

    class FilteredList(val data: List<AdapterItems>) : State()

    class ResultSearch(val data: List<AdapterItems>) : State()

    object Error : State()

    object NothingFound : State()

    object NoFavoritePlayers : State()
}
