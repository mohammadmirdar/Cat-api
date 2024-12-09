package com.mirdar.catapiapp.ui.image_list

import com.mirdar.catapiapp.domain.model.CatImage

data class ImageListState(
    val isLoading : Boolean = false,
    val error : String = "",
    val imageList : List<CatImage> = emptyList(),
    val catImage: CatImage = CatImage()
)
