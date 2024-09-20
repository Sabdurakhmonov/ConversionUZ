package uz.gita.conversionuz.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.conversionuz.domain.repository.Repository
import uz.gita.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val repositoryImpl: Repository,
    private val appNavigation: AppNavigator
) : SplashViewModel, ViewModel() {
    override fun nextToScreen() {
        repositoryImpl.getAllCurse().onEach {

        }.launchIn(viewModelScope)
    }

    override val errorMessage =  MutableSharedFlow<String>()
}