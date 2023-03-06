package com.example.testproject.data.repository

import com.example.testproject.data.model.ImageData
import com.example.testproject.data.remote.ImageAPI
import com.example.testproject.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ImageProviderRepositoryImpl @Inject constructor(
    private val api: ImageAPI,
): ImageProviderRepository {

    override fun fetchImageData(): Flow<Resource<List<ImageData>>> = flow {
        emit(Resource.Loading())

        try {
            val imageDataList = api.getPhotos()
            emit(Resource.Success(imageDataList))
        } catch(e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
            ))
        }
    }

}