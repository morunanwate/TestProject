package com.example.testproject.ui.image_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.model.ImageData
import com.example.testproject.data.repository.ImageRepository
import com.example.testproject.data.util.Resource
import com.example.testproject.data.util.Status
import com.example.testproject.ui.common.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel(), ImageDataAdapter.ImageListener {

    private val _imageList = MutableStateFlow<Resource<List<ImageData>>>(Resource.Loading())
    val imageList: StateFlow<Resource<List<ImageData>>> = _imageList

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    private val _uiEvent = MutableLiveData<UIEvent>()
    val uiEvent: LiveData<UIEvent> = _uiEvent


    init {
        fetchImageList()
    }

    private fun fetchImageList() {
        viewModelScope.launch {
            imageRepository.fetchImageDataList()
                .catch { e ->
                    _imageList.value = Resource.Error(e.toString())
                    _showProgressBar.postValue(false)
                }.onStart {
                    _showProgressBar.postValue(true)
                }
                .collect {
                    when(it.status) {
                        Status.SUCCESS -> _showProgressBar.postValue(false)
                        Status.ERROR -> _showProgressBar.postValue(false)
                        Status.LOADING -> _showProgressBar.postValue(true)
                    }
                    _imageList.value = it
                }
        }
    }

    override fun onImageClicked(imageData: ImageData) {
        _uiEvent.value = UIEvent.ImageClicked(imageData)
    }

}