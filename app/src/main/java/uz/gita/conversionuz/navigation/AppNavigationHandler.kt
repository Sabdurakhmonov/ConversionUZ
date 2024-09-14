package uz.gita.conversionuz.navigation

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.navigation.AppNavigationArgs

interface AppNavigationHandler {
    val navigationStack : Flow<AppNavigationArgs>
}