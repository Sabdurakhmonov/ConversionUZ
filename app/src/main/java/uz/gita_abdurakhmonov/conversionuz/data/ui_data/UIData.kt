package uz.gita_abdurakhmonov.conversionuz.data.ui_data

import java.io.Serializable

data class UIData(
        val id: String,
        val symbol: String,
        val name: String,
        val namEid: String,
        val rank: Int,
        val priceUsd: String,
        val percentChange24h: String,
        val percentChange1h: String,
        val percentChange7d: String,
        val priceBtc: String,
): UiData,Serializable