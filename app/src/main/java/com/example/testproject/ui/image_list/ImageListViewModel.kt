package com.example.testproject.ui.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.model.ImageData
import com.example.testproject.data.repository.ImageRepository
import com.example.testproject.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageListViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _imageList = MutableStateFlow<Resource<List<ImageData>>>(Resource.Loading())
    val imageList: StateFlow<Resource<List<ImageData>>> = _imageList


    init {
        fetchImageList()
    }

    private fun fetchImageList() {
        viewModelScope.launch {
            imageRepository.fetchImageDataList()
                .catch { e ->
                    _imageList.value = Resource.Error(e.toString())
                }
                .collect {
                    _imageList.value = it
                }
        }
    }

}