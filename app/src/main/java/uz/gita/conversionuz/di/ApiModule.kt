package uz.gita.conversionuz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.conversionuz.domain.remote.network.ApiCrypto
import uz.gita.conversionuz.domain.remote.network.ApiCurse
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun providerApiCurse(retrofit: Retrofit): ApiCurse =
        retrofit.create(ApiCurse::class.java)

    @Provides
    fun providerApiCrypto(@CryptoScope retrofit: Retrofit): ApiCrypto =
        retrofit.create(ApiCrypto::class.java)
}