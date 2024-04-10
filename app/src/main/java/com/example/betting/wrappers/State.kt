package com.example.betting.wrappers

import com.example.betting.presentation.adapter.PlayerListAdapter

sealed class State {
    class Loading(
        val data: List<PlayerListAdapter.AdapterItems>,
        val progress: Int,
        val progressVisible: Int
    ) : State()

    class ContentList(val data: List<PlayerListAdapter.AdapterItems>) : State()

    object ActivateSearch: State()

    class FilteredList(val data: List<PlayerListAdapter.AdapterItems>) : State()

    class ResultSearch(val data: List<PlayerListAdapter.AdapterItems>) : State()

    object Error : State()

    object NothingFound : State()

    object NoFavoritePlayers : State()
}
