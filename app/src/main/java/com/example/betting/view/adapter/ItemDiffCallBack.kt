package com.example.betting.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.betting.wrappers.AdapterItems
import com.example.betting.wrappers.LeagueItem
import com.example.betting.wrappers.PlayerItem

object ItemDiffCallBack : DiffUtil.ItemCallback<AdapterItems>() {
    override fun areItemsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerItem && newItem is PlayerItem -> {
                oldItem.id == newItem.id
            }
            oldItem is LeagueItem && newItem is LeagueItem -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerItem && newItem is PlayerItem -> {
                oldItem.equals(newItem)
            }
            oldItem is LeagueItem && newItem is LeagueItem -> {
                oldItem.equals(newItem)
            }
            else -> false
        }
    }
}
