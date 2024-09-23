package uz.gita_abdurakhmonov.conversionuz.data.response

import com.google.gson.annotations.SerializedName
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import java.io.Serializable

sealed interface ApiResponse {
    data class CursResponse(
        val id: Int,
        @SerializedName("Code")
        val code: String,
        @SerializedName("Ccy")
        val ccy: String,
        @SerializedName("CcyNm_RU")
        val ccyNmRU: String,
        @SerializedName("CcyNm_UZ")
        val ccyNmUZ: String,
        @SerializedName("CcyNm_UZC")
        val ccyNmUZC: String,
        @SerializedName("CcyNm_EN")
        val ccyNmEN: String,
        @SerializedName("Nominal")
        val nominal: String,
        @SerializedName("Rate")
        val rate: String,
        @SerializedName("Diff")
        val diff: String,
        @SerializedName("Date")
        val date: String,
    ) : ApiResponse, UiData,Serializable

    data class Crypto(
        val id: String,
        val symbol: String,
        val name: String,
        @SerializedName("nameid")
        val namEid: String,
        val rank: Int,
        @SerializedName("price_usd")
        val priceUsd: String,
        @SerializedName("percent_change_24h")
        val percentChange24h: String,
        @SerializedName("percent_change_1h")
        val percentChange1h: String,
        @SerializedName("percent_change_7d")
        val percentChange7d: String,
        @SerializedName("price_btc")
        val priceBtc: String,
        @SerializedName("market_cap_usd")
        val marketCapUsd: String,
        val volume24: Double,
        val volume24a: Double,
        @SerializedName("csupply")
        val cSupply: String,
        @SerializedName("tsupply")
        val tSupply: String,
        @SerializedName("msupply")
        val mSupply: String,

    ) : ApiResponse

    data class Info(
        @SerializedName("coins_num")
        val coinsNum: Int,
        val time: Int
    ) : ApiResponse

    data class CryptoResponse(
        val data: List<Crypto>,
        val info: Info
    ) : ApiResponse
}

