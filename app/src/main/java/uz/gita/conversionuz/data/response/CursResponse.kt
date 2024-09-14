package uz.gita.conversionuz.data.response

import com.google.gson.annotations.SerializedName
sealed interface ApiResponse{
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

        ):ApiResponse
    data object DataList:ArrayList<CursResponse>() {
        private fun readResolve(): Any = DataList
    }
}

