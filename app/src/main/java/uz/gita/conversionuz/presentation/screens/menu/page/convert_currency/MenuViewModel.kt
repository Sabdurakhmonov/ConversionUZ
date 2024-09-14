package uz.gita.conversionuz.presentation.screens.menu.page.convert_currency

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.data.response.ApiResponse

interface MenuViewModel {
    fun getAll()
    val errorMessage:Flow<String>
    val data:Flow<List<ApiResponse.CursResponse>>
    val searchData:Flow<Unit>
    val searchBack:Flow<Unit>
    val showPlaceHolder:Flow<Result<Unit>>
    fun clickSearch()
    fun clickBack()
    fun search(data:String)
    fun clickItem(id:Int)
}