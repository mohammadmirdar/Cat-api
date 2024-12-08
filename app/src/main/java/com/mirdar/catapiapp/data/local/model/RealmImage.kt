package com.mirdar.catapiapp.data.local.model

import com.mirdar.catapiapp.domain.model.CatImage
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmImage : RealmObject {
    @PrimaryKey
    var id: String = ""
    var url: String = ""
    var width: Int = 0
    var height: Int = 0
    var isFavourite: Boolean = false
}

fun RealmImage.toDomain(): CatImage = CatImage(id, url, width, height, isFavourite)