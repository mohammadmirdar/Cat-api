package com.mirdar.catapiapp.ui.image_list

sealed interface ImageListIntent {
    data object LoadImageList : ImageListIntent
    data class SetImageFavourite(val imageId : String, val isFavourite : Boolean) : ImageListIntent
}