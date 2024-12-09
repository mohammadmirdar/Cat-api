package com.mirdar.catapiapp.domain.model

import com.mirdar.catapiapp.data.local.model.RealmBreed
import com.mirdar.catapiapp.data.local.model.RealmImageDetail
import com.mirdar.catapiapp.data.local.model.RealmWeight
import io.realm.kotlin.ext.toRealmList

data class ImageDetail(
    val id: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val isFavourite : Boolean = false,
    val breeds: List<Breed> = emptyList()
) {
    companion object {
        val EMPTY = ImageDetail()
    }
}
fun ImageDetail.toRealm() : RealmImageDetail = RealmImageDetail().also {
    it.id = id
    it.width = width
    it.height = height
    it.url = url
    it.breeds = breeds.map { it.toRealm() }.toRealmList()
}
data class Breed(
    val weight: Weight = Weight(),
    val id: String = "",
    val name: String = "",
    val temperament: String = "",
    val origin: String = "",
    val countryCodes: String = "",
    val countryCode: String = "",
    val lifeSpan: String = "",
    val wikipediaUrl: String = ""
)

fun Breed.toRealm() : RealmBreed = RealmBreed().also {
    it.weight = weight.toRealm()
    it.id = id
    it.name = name
    it.temperament = temperament
    it.origin = origin
    it.countryCodes = countryCodes
    it.countryCode = countryCode
    it.lifeSpan = lifeSpan
    it.wikipediaUrl = wikipediaUrl
}

data class Weight(
    val imperial: String = "",
    val metric: String = ""
)

fun Weight.toRealm() : RealmWeight = RealmWeight().also {
    it.imperial = imperial
    it.metric = metric
}
