package uz.gita_abdurakhmonov.conversionuz.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import uz.gita_abdurakhmonov.conversionuz.domain.repository.Repository
import uz.gita_abdurakhmonov.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val repositoryImpl: Repository,
    private val appNavigation: AppNavigator
) : SplashViewModel, ViewModel() {
    override fun nextToScreen() {
        repositoryImpl.getAllCurse()
            .onCompletion {
                delay(1000)
                appNavigation.navigateTo(SplashScreenDirections.actionSplashScreenToMenuScreen())
            }
            .launchIn(viewModelScope)
    }

    override val errorMessage = MutableSharedFlow<String>()
}