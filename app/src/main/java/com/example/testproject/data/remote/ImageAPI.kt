package com.example.testproject.data.remote

import com.example.testproject.data.model.ImageData
import retrofit2.http.GET

interface ImageAPI {

    @GET("/photos")
    suspend fun getPhotos(): List<ImageData>

}