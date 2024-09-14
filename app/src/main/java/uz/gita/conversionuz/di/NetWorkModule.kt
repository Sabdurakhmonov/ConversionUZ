package uz.gita.conversionuz.di

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
    fun providesChuck(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context).build()

    @Provides
    fun providesOkHttpClient(chucker: ChuckerInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(chucker)
        .build()

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/json/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}