package com.compose.weatherforecastapp.domain.model.currentWeather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    @SerialName("all")
    val all: Int?
)