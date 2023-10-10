package com.example.betting.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.betting.databinding.ItemBinding
import com.example.betting.model.PlayerItem

class DiscoverAdapter: ListAdapter<PlayerItem, ItemViewHolder>(ItemDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvName.text = item.firstname + " " + item.lastname
            tvBirthdayCountry.text = item.birthCountry
            Glide.with(holder.itemView.context).load(item.photo).into(ivPhoto)
            Glide.with(holder.itemView.context).load(item.leagueLogo).into(ivLeagueLogo)
        }
    }
}