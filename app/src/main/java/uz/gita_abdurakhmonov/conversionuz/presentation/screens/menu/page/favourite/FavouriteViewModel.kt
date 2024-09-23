package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.favourite

import kotlinx.coroutines.flow.Flow
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData

interface FavouriteViewModel {
    val network:Flow<Boolean>
    val cryptoData: Flow<List<UIData>>
    val currencyData:Flow<List<ApiResponse.CursResponse>>
    val dialog:Flow<Unit>
    fun getSavedCurrency()
    fun getSavedCrypto()
    fun clearAllCurrency()
    fun clearAllCrypto()
    fun deleteCurrency(data: ApiResponse.CursResponse):Flow<Unit>
    fun deleteCrypto(data:UIData):Flow<Unit>
}