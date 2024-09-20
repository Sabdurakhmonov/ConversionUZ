package uz.gita.conversionuz.presentation.screens.menu.page.convert_crypto

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.data.ui_data.UIData

interface CryptoViewModel {
    fun getAll()
    val errorMessage: Flow<String>
    val data: Flow<List<UIData>>
    val searchData: Flow<Unit>
    val searchBack: Flow<Unit>
    val showPlaceHolder: Flow<Result<Unit>>
    fun clickSearch()
    fun clickBack()
    fun search(data:String)
}