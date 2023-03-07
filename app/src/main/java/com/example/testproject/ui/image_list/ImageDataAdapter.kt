package com.example.testproject.ui.image_list

import com.example.testproject.R
import com.example.testproject.data.model.ImageData
import com.example.testproject.databinding.ImageListItemBinding
import com.example.testproject.ui.common.base.BaseAdapter

class ImageDataAdapter(
    list: List<ImageData>,
    private val imageListener: ImageListener
    ) : BaseAdapter<ImageListItemBinding, ImageData>(list) {

    interface ImageListener {
        fun onImageClicked(imageData: ImageData)
    }

    override val layoutId: Int = R.layout.image_list_item

    override fun bind(binding: ImageListItemBinding, item: ImageData) {
        binding.apply {
            imageData = item
            listener = imageListener
            executePendingBindings()
        }
    }

}

