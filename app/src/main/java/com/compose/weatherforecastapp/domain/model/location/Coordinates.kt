package com.compose.weatherforecastapp.domain.model.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class Coordinates(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double
)