package uz.gita_abdurakhmonov.conversionuz.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @CryptoScope
    fun providesCryptoApi(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coinlore.net/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}