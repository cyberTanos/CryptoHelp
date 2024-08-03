package com.tanya.finhelp.domain

import com.tanya.finhelp.data.Api
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: Api
) : CoinRepository {

    override suspend fun getCoins(): List<Coin> {
        return api.getCoins().toDomain()
    }

    override suspend fun getHistoryCoin(id: String): List<CoinInfo> {
        return api.getHistoryCoin(id).toDomain()
    }
}

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getHistoryCoin(id: String): List<CoinInfo>
}
