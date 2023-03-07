package com.example.testproject.data.repository

import com.example.testproject.data.model.ImageData
import com.example.testproject.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun fetchImageDataList(): Flow<Resource<List<ImageData>>>
}