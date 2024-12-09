package com.mirdar.catapiapp.data.remote.model

import com.mirdar.catapiapp.domain.model.Breed
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.domain.model.Weight

data class ImageDetailRes(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val breeds: List<BreedRes>?
)

fun ImageDetailRes.toDomain(): ImageDetail {
    return ImageDetail(
        id, width, height, url, false, breeds?.map { it.toDomain() } ?: emptyList()
    )
}

data class BreedRes(
    val weight: WeightRes,
    val id: String?,
    val name: String?,
    val temperament: String?,
    val origin: String?,
    val countryCodes: String?,
    val countryCode: String?,
    val lifeSpan: String?,
    val wikipediaUrl: String?
)

fun BreedRes.toDomain(): Breed = Breed(
    weight.toDomain(),
    id ?: "",
    name ?: "",
    temperament ?: "",
    origin ?: "",
    countryCodes ?: "",
    countryCode ?: "",
    lifeSpan ?: "",
    wikipediaUrl ?: ""
)

data class WeightRes(
    val imperial: String,
    val metric: String
)

fun WeightRes.toDomain(): Weight = Weight(imperial, metric)