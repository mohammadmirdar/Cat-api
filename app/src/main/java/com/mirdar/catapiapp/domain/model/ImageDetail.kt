package com.mirdar.catapiapp.domain.model

data class ImageDetail(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val breeds: List<Breed>
)

data class Breed(
    val weight: Weight,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val lifeSpan: String,
    val wikipediaUrl: String
)

data class Weight(
    val imperial: String,
    val metric: String
)
