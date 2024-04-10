package com.example.betting.domain.models

import com.example.betting.presentation.adapter.PlayerListAdapter

data class League(
    val name: String,
    val logo: String
): PlayerListAdapter.AdapterItems