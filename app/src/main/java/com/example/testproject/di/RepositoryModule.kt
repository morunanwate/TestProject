package com.example.testproject.di

import com.example.testproject.data.repository.ImageProviderRepository
import com.example.testproject.data.repository.ImageProviderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImageProviderRepository(
        imageProviderRepositoryImpl: ImageProviderRepositoryImpl
    ): ImageProviderRepository

}