package uz.gita.conversionuz.presentation.screens.menu.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.conversionuz.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor( private val appNavigator: AppNavigator):MenuViewModel,ViewModel() {
    override fun listener(id: Int) {
        viewModelScope.launch {
            appNavigator.navigateTo(MenuScreenDirections.actionMenuScreenToInfoScreen(id))
        }
    }
}