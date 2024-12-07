package com.mirdar.catapiapp.data.local.model

import io.realm.kotlin.types.RealmObject

class RealmWeight : RealmObject {
    var imperial: String = ""
    var metric: String = ""
}