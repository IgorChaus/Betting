package com.example.betting.presentation.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player

object ItemDiffCallBack : DiffUtil.ItemCallback<PlayerListAdapter.AdapterItems>() {

    override fun areItemsTheSame(
        oldItem: PlayerListAdapter.AdapterItems,
        newItem: PlayerListAdapter.AdapterItems
    ): Boolean {
        return when{
            oldItem is Player && newItem is Player -> {
                oldItem.id == newItem.id
            }
            oldItem is League && newItem is League -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: PlayerListAdapter.AdapterItems,
        newItem: PlayerListAdapter.AdapterItems
    ): Boolean {
        return when{
            oldItem is Player && newItem is Player -> {
                oldItem == newItem
            }
            oldItem is League && newItem is League -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
