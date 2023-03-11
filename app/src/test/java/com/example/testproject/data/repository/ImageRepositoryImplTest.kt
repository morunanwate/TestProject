package com.example.testproject.data.repository

import app.cash.turbine.test
import com.example.testproject.data.model.ImageData
import com.example.testproject.data.remote.ImageAPI
import com.example.testproject.data.util.Resource
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class ImageRepositoryImplTest {

    @Mock
    lateinit var imageAPI: ImageAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun fetchImageDataListTest_EmptyList() = runTest {
        Mockito.`when`(imageAPI.getPhotos()).thenReturn(emptyList())
        val imageRepositoryImpl = ImageRepositoryImpl(imageAPI)
        imageRepositoryImpl.fetchImageDataList().test {
            val imageListInitialResource = awaitItem()
            Assert.assertEquals(true, imageListInitialResource is Resource.Loading)

            val imageListFinalResource = awaitItem()
            Assert.assertEquals(true, imageListFinalResource is Resource.Success)
            Assert.assertEquals(0, imageListFinalResource.data!!.size)
            awaitComplete()
        }
    }

    @Test
    fun fetchImageDataListTest_expectedProductList() = runTest {
        Mockito.`when`(imageAPI.getPhotos()).thenReturn(
            listOf(
                ImageData(1, 1, "", "First Image", ""),
                ImageData(1, 2, "", "Second Image", "")
            )
        )
        val imageRepositoryImpl = ImageRepositoryImpl(imageAPI)
        imageRepositoryImpl.fetchImageDataList().test {
            val imageListInitialResource = awaitItem()
            Assert.assertEquals(true, imageListInitialResource is Resource.Loading)

            val imageListFinalResource = awaitItem()
            Assert.assertEquals(true, imageListFinalResource is Resource.Success)
            Assert.assertEquals(2, imageListFinalResource.data!!.size)
            Assert.assertEquals("Second Image", imageListFinalResource.data!![1].title)
            awaitComplete()
        }
    }

    @Test
    fun fetchImageDataListTest_expectedError() = runTest {
        val response: Response<ImageData> = Response.error(401, "Unauthorized".toResponseBody())
        Mockito.`when`(imageAPI.getPhotos()).thenThrow(HttpException(response))
        val imageRepositoryImpl = ImageRepositoryImpl(imageAPI)
        imageRepositoryImpl.fetchImageDataList().test {
            val imageListInitialResource = awaitItem()
            Assert.assertEquals(true, imageListInitialResource is Resource.Loading)

            val imageListFinalResource = awaitItem()
            Assert.assertEquals(true, imageListFinalResource is Resource.Error)
            Assert.assertEquals("Oops, something went wrong!", imageListFinalResource.message)
            awaitComplete()
        }
    }

}