package uz.gita_abdurakhmonov.conversionuz.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.SavedData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData


interface Repository {
    fun getAllCurse():Flow<Result<List<ApiResponse.CursResponse>>>
    fun getAllCrypto():Flow<Result<List<UIData>>>
    fun getAllLocalCrypto():Flow<Result<List<UIData>>>
    fun getAllLocalCurrency():Flow<Result<List<ApiResponse.CursResponse>>>
    fun addData(data: SavedData):Flow<Result<Unit>>
    fun deleteAll(type: String):Flow<Result<Unit>>
    fun deleteData(data: SavedData):Flow<Result<Unit>>
}