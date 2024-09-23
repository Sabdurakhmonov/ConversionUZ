package uz.gita_abdurakhmonov.conversionuz.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita_abdurakhmonov.conversionuz.utils.NetworkStatus
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkStatus {
    @Provides
    @Singleton
    fun providesNetwork(@ApplicationContext context: Context):NetworkStatus = NetworkStatus(context)
}