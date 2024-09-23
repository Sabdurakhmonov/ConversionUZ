package uz.gita_abdurakhmonov.conversionuz.presentation.screens.splash

import kotlinx.coroutines.flow.Flow

interface SplashViewModel {
    fun nextToScreen()
    val errorMessage:Flow<String>
}