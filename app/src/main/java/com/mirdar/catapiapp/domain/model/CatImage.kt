package com.mirdar.catapiapp.domain.model

import com.mirdar.catapiapp.data.local.model.RealmImage

data class CatImage(
    val id : String = "",
    val url : String = "",
    val width : Int = 0,
    val height : Int = 0,
    val isFavourite : Boolean = false
) {
    companion object {
        val EMPTY = CatImage()
    }
}


fun CatImage.toRealm() : RealmImage = RealmImage().also {
    it.id = id
    it.url = url
    it.width = width
    it.height = height
    it.isFavourite = isFavourite
}
