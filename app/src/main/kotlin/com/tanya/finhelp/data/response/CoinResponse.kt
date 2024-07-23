package com.tanya.finhelp.data.response

import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("current_price") val price: Float?,
    @SerializedName("market_cap_rank") val rank: Int?,
    @SerializedName("price_change_24h") val priceChange: Float?,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage: Float?
)
