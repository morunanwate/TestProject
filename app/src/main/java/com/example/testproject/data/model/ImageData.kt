package com.example.testproject.data.model

import com.example.testproject.ui.common.base.ListAdapterItem

data class ImageData(
    val albumId: Int,
    override val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) : ListAdapterItem, java.io.Serializable