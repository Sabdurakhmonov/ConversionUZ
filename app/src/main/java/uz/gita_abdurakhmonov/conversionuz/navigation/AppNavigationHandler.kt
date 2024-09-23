package uz.gita_abdurakhmonov.conversionuz.navigation

import kotlinx.coroutines.flow.Flow

interface AppNavigationHandler {
    val navigationStack : Flow<AppNavigationArgs>
}