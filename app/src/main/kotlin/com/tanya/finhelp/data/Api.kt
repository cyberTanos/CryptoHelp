package com.tanya.finhelp.data

import com.tanya.finhelp.data.response.CoinInfoResponse
import com.tanya.finhelp.data.response.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOKEN = "GDVBgpky1gW42iSKRZEmCNDX"
private const val CURRENCY = "usd"
private const val DAYS = "100"
private const val INTERVAL = ""
private const val PRECISION = "2"

interface Api {

    @GET("coins/markets")
    suspend fun getCoins(
        @Header("x-cg-api-key") value: String = TOKEN,
        @Query("vs_currency") currency: String = CURRENCY
    ): List<CoinResponse>

    @GET("coins/{id}/market_chart")
    suspend fun getHistoryCoin(
        @Path("id") id: String,
        @Header("x-cg-api-key") value: String = TOKEN,
        @Query("vs_currency") currency: String = CURRENCY,
        @Query("days") days: String = DAYS,
        @Query("interval") interval: String = INTERVAL,
        @Query("precision") precision: String = PRECISION,
    ): CoinInfoResponse
}
