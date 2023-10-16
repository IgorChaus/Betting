package com.example.betting.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.betting.wrappers.AdapterItems
import com.example.betting.wrappers.LeagueItemAdapter
import com.example.betting.wrappers.PlayerItemAdapter

object ItemDiffCallBack : DiffUtil.ItemCallback<AdapterItems>() {
    override fun areItemsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerItemAdapter && newItem is PlayerItemAdapter -> {
                oldItem.id == newItem.id
            }
            oldItem is LeagueItemAdapter && newItem is LeagueItemAdapter -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerItemAdapter && newItem is PlayerItemAdapter -> {
                oldItem.equals(newItem)
            }
            oldItem is LeagueItemAdapter && newItem is LeagueItemAdapter -> {
                oldItem.equals(newItem)
            }
            else -> false
        }
    }
}
