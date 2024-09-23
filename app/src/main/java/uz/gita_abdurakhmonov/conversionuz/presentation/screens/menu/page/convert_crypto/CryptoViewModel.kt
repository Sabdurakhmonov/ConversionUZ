package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.convert_crypto

import kotlinx.coroutines.flow.Flow
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData

interface CryptoViewModel {
    val network:Flow<Boolean>
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