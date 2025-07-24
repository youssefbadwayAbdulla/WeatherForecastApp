package com.compose.weatherforecastapp.domain.model.currentWeather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    @SerialName("1h")
    val h: Double?
)