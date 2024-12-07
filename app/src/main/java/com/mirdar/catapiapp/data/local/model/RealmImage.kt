package com.mirdar.catapiapp.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmImage : RealmObject {
    @PrimaryKey
    var id: String = ""
    var url: String = ""
    var width: Int = 0
    var height: Int = 0
    var isFavourite: Boolean = false
}