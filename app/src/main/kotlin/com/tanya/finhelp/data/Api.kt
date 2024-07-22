package com.tanya.finhelp.data

import com.tanya.finhelp.data.response.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val TOKEN = "GDVBgpky1gW42iSKRZEmCNDX"
private const val CURRENCY = "usd"

interface Api {

    @GET("coins/markets")
    suspend fun getCoins(
        @Header("x-cg-api-key") value: String = TOKEN,
        @Query("vs_currency") currency: String = CURRENCY
    ): List<CoinResponse>
}
