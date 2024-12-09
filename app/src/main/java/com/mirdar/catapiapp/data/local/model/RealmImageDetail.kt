package com.mirdar.catapiapp.data.local.model

import com.mirdar.catapiapp.domain.model.Breed
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.domain.model.Weight
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmImageDetail : RealmObject {
    @PrimaryKey
    var id: String = ""
    var width: Int = 0
    var height: Int = 0
    var url: String = ""
    var isFavourite : Boolean = false
    var breeds: RealmList<RealmBreed> = realmListOf()
}

fun RealmImageDetail.toDomain(): ImageDetail =
    ImageDetail(id, width, height, url,isFavourite, breeds.map { it.toDomain() })

fun RealmBreed.toDomain(): Breed = Breed(
    weight?.toDomain() ?: Weight("", ""),
    id, name, temperament, origin, countryCodes, countryCode, lifeSpan, wikipediaUrl
)

fun RealmWeight.toDomain(): Weight = Weight(imperial, metric)