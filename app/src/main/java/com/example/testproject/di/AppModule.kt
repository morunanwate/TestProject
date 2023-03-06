package com.example.testproject.di

import com.example.testproject.data.remote.ImageAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageAPI(): ImageAPI {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(ImageAPI::class.java)
    }

}