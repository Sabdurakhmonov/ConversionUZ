package uz.gita_abdurakhmonov.conversionuz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita_abdurakhmonov.conversionuz.domain.repository.Repository
import uz.gita_abdurakhmonov.conversionuz.domain.repository.RepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl):Repository
}