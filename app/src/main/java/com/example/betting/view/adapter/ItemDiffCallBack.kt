package com.example.betting.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.betting.model.PlayerItem

object ItemDiffCallBack : DiffUtil.ItemCallback<PlayerItem>() {
    override fun areItemsTheSame(oldItem: PlayerItem, newItem: PlayerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerItem, newItem: PlayerItem): Boolean {
        return oldItem == newItem
    }
}