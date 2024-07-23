package com.tanya.finhelp.domain

import com.tanya.finhelp.data.response.CoinResponse
import com.tanya.finhelp.util.orZero

fun List<CoinResponse>.toDomain(): List<Coin> {
    return this.map {
        Coin(
            id = it.id.orEmpty(),
            symbol = it.symbol.orEmpty(),
            name = it.name.orEmpty(),
            image = it.image.orEmpty(),
            price = it.price.orZero().toString(),
            rank = it.rank.orZero().toString(),
            priceChange = it.priceChange.orZero().toString(),
            priceChangePercentage = it.priceChangePercentage.orZero().toString()
        )
    }
}
