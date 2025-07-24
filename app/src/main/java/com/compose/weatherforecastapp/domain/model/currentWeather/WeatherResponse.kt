package com.compose.weatherforecastapp.domain.model.currentWeather


import com.compose.weatherforecastapp.domain.model.location.Coordinates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("base")
    val base: String?,
    @SerialName("clouds")
    val clouds: Clouds?,
    @SerialName("cod")
    val cod: Int?,
    @SerialName("coord")
    val coord: Coordinates?,
    @SerialName("dt")
    val dt: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("main")
    val main: Main?,
    @SerialName("name")
    val name: String?,
    @SerialName("rain")
    val rain: Rain?,
    @SerialName("sys")
    val sys: Sys?,
    @SerialName("timezone")
    val timezone: Int?,
    @SerialName("visibility")
    val visibility: Int?,
    @SerialName("weather")
    val weather: List<Weather?>?,
    @SerialName("wind")
    val wind: Wind?
)