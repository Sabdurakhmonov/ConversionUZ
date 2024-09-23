package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita_abdurakhmonov.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SettingViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SettingViewModel,ViewModel(){

}