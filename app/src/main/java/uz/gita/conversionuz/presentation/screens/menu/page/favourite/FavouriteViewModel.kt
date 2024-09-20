package uz.gita.conversionuz.presentation.screens.menu.page.favourite

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.data.ui_data.UIData
import uz.gita.conversionuz.data.ui_data.UiData

interface FavouriteViewModel {
    val cryptoData: Flow<List<UIData>>
    val currencyData:Flow<List<ApiResponse.CursResponse>>
    val dialog:Flow<Unit>
    fun getSavedCurrency()
    fun getSavedCrypto()
    fun clearAllCurrency()
    fun clearAllCrypto()
    fun deleteCurrency(data: ApiResponse.CursResponse)
    fun deleteCrypto(data:UIData)
}