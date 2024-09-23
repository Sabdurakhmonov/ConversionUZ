package uz.gita_abdurakhmonov.conversionuz.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {
    override val navigationStack = MutableSharedFlow<AppNavigationArgs>()

    private suspend fun navigator(args: AppNavigationArgs) {
        navigationStack.emit(args)
    }

    override suspend fun navigateTo(direction: NavDirections) = navigator {
        navigate(direction)
    }

    override suspend fun back() = navigator {
        navigateUp()
    }
}