package com.tanya.finhelp.data.response

import com.google.gson.annotations.SerializedName

data class CoinInfoResponse(
    @SerializedName("prices") val prices: List<List<Double?>?>?
)
