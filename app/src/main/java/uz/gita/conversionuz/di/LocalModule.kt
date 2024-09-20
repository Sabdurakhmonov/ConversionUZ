package uz.gita.conversionuz.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.conversionuz.domain.remote.local.AppDao
import uz.gita.conversionuz.domain.remote.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun providesRoom(@ApplicationContext context: Context):AppDatabase = Room.databaseBuilder(
        context,AppDatabase::class.java, "local"
    ).build()

    @Provides
    fun providesDao(appDatabase: AppDatabase): AppDao = appDatabase.saved()
}