package uz.gita.conversionuz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.conversionuz.navigation.AppNavigationHandler
import uz.gita.conversionuz.navigation.AppNavigator
import uz.gita.conversionuz.navigation.AppNavigatorDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigateModule {
    @Binds
    @Singleton
    fun providesAppNavigator(impl: AppNavigatorDispatcher): AppNavigator

    @Binds
    @Singleton
    fun providesAppNavigatorHandler(impl: AppNavigatorDispatcher): AppNavigationHandler
}