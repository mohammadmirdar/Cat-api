package com.mirdar.catapiapp.data.local

import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail

interface LocalRepository {
    suspend fun insertImage(image: CatImage)
    suspend fun insertImageDetail(imageDetail: ImageDetail)
    suspend fun readImageList(): List<CatImage>
    suspend fun readImageDetail(imageId: String): ImageDetail?
    suspend fun setImageFavourite(imageId: String, isFavourite: Boolean)
    suspend fun clearImageList()
}