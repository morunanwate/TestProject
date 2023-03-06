package com.example.testproject.data.remote

import com.example.testproject.data.model.ImageData
import retrofit2.http.GET

interface ImageFetchingAPI {

    @GET("/photos")
    suspend fun getPhotos(): List<ImageData>

}