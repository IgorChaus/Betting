package com.example.betting.domain.models

import com.example.betting.presentation.adapter.PlayerListAdapter

data class League(
    val id: Int,
    val name: String,
    val logo: String
): PlayerListAdapter.AdapterItems