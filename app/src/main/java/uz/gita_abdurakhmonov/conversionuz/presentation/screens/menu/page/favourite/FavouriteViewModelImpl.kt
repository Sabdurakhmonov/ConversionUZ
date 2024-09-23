package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.SavedData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.domain.repository.Repository
import uz.gita_abdurakhmonov.conversionuz.utils.NetworkStatus
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val networkStatus: NetworkStatus
) : FavouriteViewModel, ViewModel() {
    override val network = MutableSharedFlow<Boolean>()

    override val cryptoData =
        MutableSharedFlow<List<UIData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val currencyData = MutableSharedFlow<List<ApiResponse.CursResponse>>()

    override val dialog = MutableSharedFlow<Unit>()

    override fun getSavedCurrency() {
        viewModelScope.launch {
            repository.getAllLocalCurrency().collect {
                it.onSuccess { list ->
                    Log.d("CCC", "getSavedCurrency: 1")
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

    override fun deleteCurrency(data: ApiResponse.CursResponse): Flow<Unit> = flow {
        repository.deleteData(SavedData(data.id,"currency")).launchIn(viewModelScope)
        emit(Unit)
    }

    override fun deleteCrypto(data: UIData): Flow<Unit> = flow {
        repository.deleteData(SavedData(data.id.toInt(),"crypto")).launchIn(viewModelScope)
        emit(Unit)
    }

    init {
        networkStatus.listenerNetwork(
            onAvailable = {
                viewModelScope.launch {
                    network.emit(true)
                }
            }, onLost = {
                viewModelScope.launch {
                    network.emit(false)
                }
            }
        )
    }
}