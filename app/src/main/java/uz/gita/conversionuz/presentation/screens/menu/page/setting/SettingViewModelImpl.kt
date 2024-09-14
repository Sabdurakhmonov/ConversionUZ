package uz.gita.conversionuz.presentation.screens.menu.page.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.conversionuz.navigation.AppNavigator
import uz.gita.conversionuz.presentation.screens.menu.screen.MenuScreenDirections
import javax.inject.Inject

@HiltViewModel
class SettingViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SettingViewModel,ViewModel(){

}