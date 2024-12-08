package com.mirdar.catapiapp.data.local.model

import io.realm.kotlin.types.RealmObject

open class RealmBreed : RealmObject {
    var weight: RealmWeight? = null
    var id: String = ""
    var name: String = ""
    var temperament: String = ""
    var origin: String = ""
    var countryCodes: String = ""
    var countryCode: String = ""
    var lifeSpan: String = ""
    var wikipediaUrl: String = ""
}