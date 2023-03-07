package com.example.testproject.ui.image_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testproject.data.model.ImageData
import com.example.testproject.util.IntentKeyConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _imageData = savedStateHandle.getLiveData<ImageData>(IntentKeyConstants.IMAGE_DETAIL_IMAGE_DATA_KAY)
    val imageData: LiveData<ImageData> = _imageData


}