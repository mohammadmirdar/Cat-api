package com.mirdar.catapiapp.ui.image_detail

sealed class ImageDetailIntent {

    data class GetImageDetail(val imageId : String) : ImageDetailIntent()
    data class MakeImageFavourite(val imageId: String, val isFavourite : Boolean) : ImageDetailIntent()
}