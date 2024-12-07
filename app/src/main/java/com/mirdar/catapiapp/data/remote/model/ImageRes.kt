package com.mirdar.catapiapp.data.remote.model

import com.mirdar.catapiapp.domain.model.CatImage

data class ImageRes(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

fun ImageRes.toDomain(): CatImage = CatImage(id, url, width, height)