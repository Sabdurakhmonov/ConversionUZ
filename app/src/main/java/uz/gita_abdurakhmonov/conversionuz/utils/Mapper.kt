package uz.gita_abdurakhmonov.conversionuz.utils

import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData

fun ApiResponse.Crypto.toUIData() =
    UIData(
        id = id,
        symbol = symbol,
        name = name,
        namEid = namEid,
        rank = rank,
        priceUsd = priceUsd,
        percentChange24h = percentChange24h,
        percentChange1h = percentChange1h,
        percentChange7d = percentChange7d,
        priceBtc = priceBtc,
    )