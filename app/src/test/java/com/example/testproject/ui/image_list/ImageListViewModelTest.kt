package com.example.testproject.ui.image_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.testproject.data.repository.ImageRepository
import com.example.testproject.data.util.Resource
import com.example.testproject.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ImageListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var imageRepository: ImageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_GetImageList_expectedSuccess() = runTest {
        Mockito.`when`(imageRepository.fetchImageDataList()).thenReturn(flow {
            emit(Resource.Success(emptyList()))
        })
        val imageListViewModel = ImageListViewModel(imageRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        imageListViewModel.imageList.test {
            val imageListResource = awaitItem()
            Assert.assertEquals(true, imageListResource is Resource.Success)
            Assert.assertEquals(0, imageListResource.data!!.size)
            val result = imageListViewModel.showProgressBar.getOrAwaitValue()
            Assert.assertEquals(false, result)
            cancel()
        }
    }

    @Test
    fun test_GetImageList_expectedError() = runTest {
        Mockito.`when`(imageRepository.fetchImageDataList()).thenReturn(flow {
            emit(Resource.Error("Something went wrong"))
        })
        val imageListViewModel = ImageListViewModel(imageRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        imageListViewModel.imageList.test {
            val imageListResource = awaitItem()
            Assert.assertEquals(true, imageListResource is Resource.Error)
            Assert.assertEquals("Something went wrong", imageListResource.message)
            val result = imageListViewModel.showProgressBar.getOrAwaitValue()
            Assert.assertEquals(false, result)
            cancel()
        }
    }


    @Test
    fun test_GetImageList_expectedLoading() = runTest {
        Mockito.`when`(imageRepository.fetchImageDataList()).thenReturn(flow {
            emit(Resource.Loading())
        })
        val imageListViewModel = ImageListViewModel(imageRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        imageListViewModel.imageList.test {
            val imageListResource = awaitItem()
            Assert.assertEquals(true, imageListResource is Resource.Loading)
            val result = imageListViewModel.showProgressBar.getOrAwaitValue()
            Assert.assertEquals(true, result)
            cancel()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


}