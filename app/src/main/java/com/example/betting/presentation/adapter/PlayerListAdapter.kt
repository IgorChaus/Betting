package com.example.betting.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betting.databinding.ItemLeagueBinding
import com.example.betting.databinding.ItemPlayerBinding
import com.example.betting.domain.models.LeagueAdapterItem
import com.example.betting.domain.models.PlayerAdapterItem

class PlayerListAdapter: ListAdapter<PlayerListAdapter.AdapterItems, RecyclerView.ViewHolder>(ItemDiffCallBack) {

    var itemClickListener: ((PlayerAdapterItem) -> Unit)? = null

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is PlayerAdapterItem -> PLAYER_ITEM
            is LeagueAdapterItem -> LEAGUE_ITEM
            else -> throw RuntimeException("Illegal item type")
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        PLAYER_ITEM -> PlayerViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )
        LEAGUE_ITEM -> LeagueViewHolder(
            ItemLeagueBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )
        else -> throw throw RuntimeException("Illegal item type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            PLAYER_ITEM -> bindPlayerItem(holder, getItem(position) as PlayerAdapterItem)
            LEAGUE_ITEM -> bindLeagueItem(holder, getItem(position) as LeagueAdapterItem)
            else -> throw RuntimeException("Illegal item type")
        }

    @SuppressLint("SetTextI18n")
    private fun bindPlayerItem(holder: RecyclerView.ViewHolder, item: PlayerAdapterItem) {
        val itemViewHolder = holder as PlayerViewHolder
        with(itemViewHolder.binding) {
            tvName.text = item.firstName + " " + item.lastName
            tvBirthdayCountry.text = item.birthCountry
            Glide.with(holder.itemView.context).load(item.photo).into(ivPhoto)
            Glide.with(holder.itemView.context).load(item.leagueLogo).circleCrop().into(ivLeagueLogo)
            holder.itemView.setOnClickListener {
                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                itemClickListener?.invoke(item)
            }
        }
    }

    private fun bindLeagueItem(holder: RecyclerView.ViewHolder, item: LeagueAdapterItem) {
        val itemViewHolder = holder as LeagueViewHolder
        with(itemViewHolder.binding) {
            tvLeague.text = item.name
            Glide.with(holder.itemView.context).load(item.logo).circleCrop().into(ivLogo)
        }
    }

    interface AdapterItems

    companion object {
        private const val PLAYER_ITEM = 1
        private const val LEAGUE_ITEM = 2
    }
}