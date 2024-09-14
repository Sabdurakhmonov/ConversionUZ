package uz.gita.conversionuz.presentation.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.supervisorScope
import uz.gita.conversionuz.domain.repository.Repository
import uz.gita.conversionuz.navigation.AppNavigator
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val repositoryImpl: Repository,
    private val appNavigation: AppNavigator
) : SplashViewModel, ViewModel() {
    override fun nextToScreen() {
        repositoryImpl.getAll().onEach {

        }.launchIn(viewModelScope)
    }

    override val errorMessage =  MutableSharedFlow<String>()
}