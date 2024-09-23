package uz.gita_abdurakhmonov.conversionuz.domain.remote.network

import retrofit2.Response
import retrofit2.http.GET
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse

interface ApiCurse {
    @GET("all/")
    suspend fun getAll():Response<List<ApiResponse.CursResponse>>
}