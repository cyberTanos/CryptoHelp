package com.tanya.finhelp.domain

import android.graphics.Color
import com.tanya.finhelp.data.response.CoinResponse
import com.tanya.finhelp.domain.Coin.Value
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
            priceChange = getValue(it.priceChange.orZero()),
            priceChangePercentage = getValue(it.priceChangePercentage.orZero())
        )
    }
}

private fun getValue(value: Float): Value {
    val color = if (value < 0) Color.RED else Color.GREEN
    return Value(
        value = value.toString(),
        color = color
    )
}
