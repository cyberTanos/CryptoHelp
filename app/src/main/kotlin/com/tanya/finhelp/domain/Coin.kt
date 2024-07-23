package com.tanya.finhelp.domain

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val price: String,
    val rank: String,
    val priceChange: String,
    val priceChangePercentage: String
)
