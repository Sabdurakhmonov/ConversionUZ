package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.convert_currency

import kotlinx.coroutines.flow.Flow
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse

interface CurrencyViewModel {
    fun getAll()
    val network:Flow<Boolean>
    val errorMessage:Flow<String>
    val data:Flow<List<ApiResponse.CursResponse>>
    val searchData:Flow<Unit>
    val searchBack:Flow<Unit>
    val showPlaceHolder:Flow<Result<Unit>>
    fun clickSearch()
    fun clickBack()
    fun search(data:String)
}