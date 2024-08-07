package com.tanya.finhelp.screens.coins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tanya.finhelp.databinding.ItemCoinBinding
import com.tanya.finhelp.databinding.ItemSkeletonCoinBinding
import com.tanya.finhelp.domain.model.BaseRecyclerItem
import com.tanya.finhelp.domain.model.Coin

private const val COIN_VH = 0
private const val SKELETON_COIN_VH = 1

class CoinsAdapter(
    private var onClick: (Coin) -> Unit
) : ListAdapter<BaseRecyclerItem, BaseVH>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return when (viewType) {
            COIN_VH -> CoinVH(
                ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick
            )

            SKELETON_COIN_VH -> SkeletonVH(
                ItemSkeletonCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        when (holder) {
            is CoinVH -> holder.bind(getItem(position))
            is SkeletonVH -> holder.bind(getItem(position))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Coin -> COIN_VH
            else -> SKELETON_COIN_VH
        }
    }

    object Differ : DiffUtil.ItemCallback<BaseRecyclerItem>() {
        override fun areItemsTheSame(oldItem: BaseRecyclerItem, newItem: BaseRecyclerItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BaseRecyclerItem, newItem: BaseRecyclerItem): Boolean {
            return if (oldItem is Coin && newItem is Coin) oldItem == newItem else false
        }
    }
}
