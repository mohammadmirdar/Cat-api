package com.mirdar.catapiapp.data.local.model

import com.mirdar.catapiapp.domain.model.Breed
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class RealmImageDetail : RealmObject {
    var id: String = ""
    var width: Int = 0
    var height: Int = 0
    var url: String = ""
    var breeds: RealmList<Breed> = realmListOf()
}