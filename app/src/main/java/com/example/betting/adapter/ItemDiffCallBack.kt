package com.example.betting.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.betting.wrappers.AdapterItems
import com.example.betting.wrappers.LeagueAdapterItem
import com.example.betting.wrappers.PlayerAdapterItem

object ItemDiffCallBack : DiffUtil.ItemCallback<AdapterItems>() {
    override fun areItemsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerAdapterItem && newItem is PlayerAdapterItem -> {
                oldItem.id == newItem.id
            }
            oldItem is LeagueAdapterItem && newItem is LeagueAdapterItem -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: AdapterItems, newItem: AdapterItems): Boolean {
        return when{
            oldItem is PlayerAdapterItem && newItem is PlayerAdapterItem -> {
                oldItem.equals(newItem)
            }
            oldItem is LeagueAdapterItem && newItem is LeagueAdapterItem -> {
                oldItem.equals(newItem)
            }
            else -> false
        }
    }
}
