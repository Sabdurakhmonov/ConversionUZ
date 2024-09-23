package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import uz.gita_abdurakhmonov.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : MenuViewModel, ViewModel() {

    override fun listener1(uiData: UiData) {
        nextToScreen(uiData)
    }

    override fun listener2(uiData: UiData) {
        nextToScreen(uiData)
    }

    override fun clickItemFavCrypto(uiData: UiData) {
        nextToScreen(uiData)
    }

    override fun clickItemFavCurr(uiData: UiData) {
        nextToScreen(uiData)
    }

    @Synchronized
    private fun nextToScreen(uiData: UiData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MenuScreenDirections.actionMenuScreenToInfoScreen(uiData))
        }
    }

    var currentPosition = 0
}