package com.tanya.finhelp.domain.mapper

import android.graphics.Color
import com.tanya.finhelp.data.response.CoinInfoResponse
import com.tanya.finhelp.data.response.CoinResponse
import com.tanya.finhelp.domain.model.Coin
import com.tanya.finhelp.domain.model.Coin.Value
import com.tanya.finhelp.domain.model.CoinInfo
import com.tanya.finhelp.util.LABEL_COUNT_GRAPH
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
            priceChangePercentage = getValuePercentage(it.priceChangePercentage.orZero())
        )
    }
}

private fun getValue(value: Float): Value {
    val price = if (value > 0) "+$value" else value
    val color = if (value < 0) Color.RED else Color.GREEN
    return Value(
        value = price.toString(),
        color = color
    )
}

private fun getValuePercentage(value: Float): Value {
    val color = if (value < 0) Color.RED else Color.GREEN
    return Value(
        value = "$value%",
        color = color
    )
}

fun CoinInfoResponse.toDomain(): List<CoinInfo> {
    return this.prices?.map {
        CoinInfo(
            date = 0.0,
            price = it?.get(1) ?: 0.0
        )
    }.orEmpty().takeLast(LABEL_COUNT_GRAPH)
}
