package uz.gita.conversionuz.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.data.response.ApiResponse


interface Repository {
    fun getAll():Flow<Result<List<ApiResponse.CursResponse>>>
}