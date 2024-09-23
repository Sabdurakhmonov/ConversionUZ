package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.convert_currency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.domain.repository.Repository
import uz.gita_abdurakhmonov.conversionuz.utils.NetworkStatus
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val networkStatus: NetworkStatus
) : CurrencyViewModel, ViewModel() {
    private var dataList = mutableListOf<ApiResponse.CursResponse>()

    private var check = false

    private fun setData(data: List<ApiResponse.CursResponse>) {
        this.dataList.addAll(data)
    }

    override fun getAll() {
        repository.getAllCurse().onEach {
            it.onSuccess { list ->
                Log.d("AAA", "getAll: ${list.size}")
                dataList = list.toMutableList()
                setData(list)
                check = true
                data.emit(list)
            }.onFailure { error ->
                errorMessage.emit(error.message!!)
            }
        }.launchIn(viewModelScope)
    }

    override val network = MutableSharedFlow<Boolean>()


    override val errorMessage = MutableSharedFlow<String>()
    override val data = MutableStateFlow<List<ApiResponse.CursResponse>>(emptyList())
    override val searchData =
        MutableSharedFlow<Unit>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val searchBack =
        MutableSharedFlow<Unit>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val showPlaceHolder =
        MutableSharedFlow<Result<Unit>>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    override fun clickSearch() {
        viewModelScope.launch {
            searchData.emit(Unit)
        }
    }

    override fun clickBack() {
        viewModelScope.launch {
            searchBack.emit(Unit)
        }
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

    override fun search(s: String) {
        if (check) {
            val dataSearch = dataList.filter {
                it.ccy.lowercase().contains(s.trim().lowercase())
                    .or(it.ccyNmUZ.lowercase().contains(s.trim().lowercase()))
            }
            viewModelScope.launch {
                if (s.isEmpty()) {
                    data.emit(dataList)
                    showPlaceHolder.emit(Result.failure(Exception("zzz")))
                } else if (s.isNotEmpty() && dataSearch.isEmpty()) {
                    showPlaceHolder.emit(Result.success(Unit))
                } else data.emit(dataSearch)
            }
        }
    }

}