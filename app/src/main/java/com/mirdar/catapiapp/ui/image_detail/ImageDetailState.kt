package com.mirdar.catapiapp.ui.image_detail

import com.mirdar.catapiapp.domain.model.ImageDetail

data class ImageDetailState(
    val isLoading: Boolean = false,
    val error: String = "",
    val imageDetail: ImageDetail = ImageDetail.EMPTY
)