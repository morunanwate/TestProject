package com.example.testproject.ui.common.util

import com.example.testproject.data.model.ImageData

sealed class UIEvent {
    data class ImageClicked(val imageData: ImageData): UIEvent()
}