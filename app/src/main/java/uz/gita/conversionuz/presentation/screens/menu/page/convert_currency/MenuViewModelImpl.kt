package uz.gita.conversionuz.presentation.screens.menu.page.convert_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.domain.repository.Repository
import uz.gita.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val appNavigation: AppNavigator,
) : MenuViewModel,
    ViewModel() {
    private var dataList = mutableListOf<ApiResponse.CursResponse>()

    private var check = false

    private fun setData(data:List<ApiResponse.CursResponse>){
        this.dataList.addAll(data)
    }
    override fun getAll() {
        repository.getAll().onEach {
            it.onSuccess { list ->
                delay(1000)
                dataList = list.toMutableList()
                setData(list)
                check = true
                data.emit(list)
            }.onFailure { error ->
                errorMessage.emit(error.message!!)
            }
        }.launchIn(viewModelScope)
    }

    override val errorMessage = MutableSharedFlow<String>()
    override val data = MutableSharedFlow<List<ApiResponse.CursResponse>>()
    override val searchData = MutableSharedFlow<Unit>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val searchBack = MutableSharedFlow<Unit>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val showPlaceHolder =  MutableSharedFlow<Result<Unit>>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)


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
                it.ccy.lowercase().contains(s.trim().lowercase())
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

    override fun clickItem(id: Int) {

    }
}