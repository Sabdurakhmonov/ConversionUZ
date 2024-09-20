package uz.gita.conversionuz.presentation.screens.menu.page.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.data.ui_data.SavedData
import uz.gita.conversionuz.data.ui_data.UIData
import uz.gita.conversionuz.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModelImpl @Inject constructor(
    private val repository: Repository
) : FavouriteViewModel, ViewModel() {
    override val cryptoData =
        MutableSharedFlow<List<UIData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val currencyData = MutableSharedFlow<List<ApiResponse.CursResponse>>()

    override val dialog = MutableSharedFlow<Unit>()

    override fun getSavedCurrency() {
        viewModelScope.launch {
            repository.getAllLocalCurrency().collect {
                it.onSuccess { list ->
                    currencyData.emit(list)
                }.onFailure {

                }
            }
        }
    }

    override fun getSavedCrypto() {
        viewModelScope.launch {
            repository.getAllLocalCrypto().collect {
                it.onSuccess { list ->
                    cryptoData.emit(list)
                }
            }
        }
    }

    override fun clearAllCurrency() {
        repository.deleteAll("currency").launchIn(viewModelScope)
    }

    override fun clearAllCrypto() {
        repository.deleteAll("crypto").launchIn(viewModelScope)
    }

    override fun deleteCurrency(data: ApiResponse.CursResponse) {
        repository.deleteData(SavedData(data.id,"currency")).launchIn(viewModelScope)
    }

    override fun deleteCrypto(data: UIData) {
        repository.deleteData(SavedData(data.id.toInt(),"crypto")).launchIn(viewModelScope)
    }
}