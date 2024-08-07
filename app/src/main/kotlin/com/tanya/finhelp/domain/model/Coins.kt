package com.tanya.finhelp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface BaseRecyclerItem

@Parcelize
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val price: String,
    val rank: String,
    val priceChange: Value,
    val priceChangePercentage: Value
) : Parcelable, BaseRecyclerItem {

    @Parcelize
    class Value(
        val value: String,
        val color: Int
    ): Parcelable
}

class SkeletonCoin : BaseRecyclerItem
