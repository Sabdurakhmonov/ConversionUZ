package uz.gita_abdurakhmonov.conversionuz.domain.remote.network

import retrofit2.Response
import retrofit2.http.GET
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse

interface ApiCrypto {
    @GET("tickers/")
    suspend fun getAll():Response<ApiResponse.CryptoResponse>
}