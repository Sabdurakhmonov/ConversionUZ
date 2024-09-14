package uz.gita.conversionuz.navigation

import androidx.navigation.NavDirections

interface AppNavigator {
    suspend fun navigateTo(direction:NavDirections)
    suspend fun back()
}