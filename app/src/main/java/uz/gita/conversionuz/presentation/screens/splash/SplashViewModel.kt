package uz.gita.conversionuz.presentation.screens.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SplashViewModel {
    fun nextToScreen()
    val errorMessage:Flow<String>
}