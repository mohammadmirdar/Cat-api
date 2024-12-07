package com.mirdar.catapiapp.domain.model

import com.mirdar.catapiapp.data.local.model.RealmImage

data class CatImage(
    val id : String,
    val url : String,
    val width : Int,
    val height : Int,
    val isFavourite : Boolean
)

fun CatImage.toRealm() : RealmImage = RealmImage().also {
    it.id = id
    it.url = url
    it.width = width
    it.height = height
    it.isFavourite = isFavourite
}
