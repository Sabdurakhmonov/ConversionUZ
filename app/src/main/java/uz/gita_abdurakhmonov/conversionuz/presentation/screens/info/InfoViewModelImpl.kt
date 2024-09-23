package uz.gita_abdurakhmonov.conversionuz.presentation.screens.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.SavedData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import uz.gita_abdurakhmonov.conversionuz.domain.repository.Repository
import uz.gita_abdurakhmonov.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
):InfoViewModel,ViewModel() {
    override val data = MutableSharedFlow<List<UiData>>()
    override val errorMessage =  MutableStateFlow("")

    override fun getAll(type: String) {
        when(type){
            "currency"->{
                repository.getAllCurse().onEach {
                    it.onSuccess {
                        data.emit(it)
                    }.onFailure {
                        errorMessage.emit(it.message!!)
                    }
                }.launchIn(viewModelScope)
            }
            else ->{
                repository.getAllCrypto().onEach {
                    it.onSuccess {
                        data.emit(it)
                    }.onFailure {
                        errorMessage.emit(it.message!!)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    override fun clickBack() {
        viewModelScope.launch {
            appNavigator.back()
        }
    }

    override fun save(uiData: UiData) {
        when(uiData){
            is UIData->{
                repository.addData(SavedData(uiData.id.toInt(),"crypto")).onEach {
                    it.onSuccess {
                        errorMessage.emit("success")
                    }.onFailure {
                        errorMessage.emit(it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }
            is ApiResponse.CursResponse->{
                repository.addData(SavedData(uiData.id,"currency")).onEach {
                    it.onSuccess {
                        errorMessage.emit("success")
                    }.onFailure {
                        errorMessage.emit(it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }
        }

    }
}
