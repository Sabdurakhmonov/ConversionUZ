package uz.gita.conversionuz.presentation.screens.menu.page.convert_crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.conversionuz.data.ui_data.UIData
import uz.gita.conversionuz.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class CryptoViewModelImpl @Inject constructor(
    private val repository: Repository
): CryptoViewModel,ViewModel() {
    private var dataList = mutableListOf<UIData>()

    private var check = false

    override fun getAll() {
        repository.getAllCrypto().onEach {
            it.onSuccess { list ->
                dataList = list.toMutableList()
                check = true
                data.emit(list)
            }.onFailure { error ->
                errorMessage.emit(error.message!!)
            }
        }.launchIn(viewModelScope)
    }

    override val errorMessage =  MutableSharedFlow<String>()

    override val data = MutableSharedFlow<List<UIData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val searchData = MutableSharedFlow<Unit>()

    override val searchBack = MutableSharedFlow<Unit>()

    override val showPlaceHolder = MutableSharedFlow<Result<Unit>>()


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

    override fun search(s: String) {
        if (check) {
            val dataSearch = dataList.filter {
                it.symbol.lowercase().contains(s.trim().lowercase()).or(it.namEid.lowercase().contains(s.trim().lowercase()))
            }
            viewModelScope.launch {
                if (s.isEmpty()) {
                    data.emit(dataList)
                    showPlaceHolder.emit(Result.failure(Exception("zzz")))
                } else if(s.isNotEmpty()&&dataSearch.isEmpty()){
                    showPlaceHolder.emit(Result.success(Unit))
                }else data.emit(dataSearch)
            }
        }
    }
}