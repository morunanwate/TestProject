package com.example.testproject.di

import com.example.testproject.data.repository.ImageRepository
import com.example.testproject.data.repository.ImageRepositoryImpl
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
        imageProviderRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

}