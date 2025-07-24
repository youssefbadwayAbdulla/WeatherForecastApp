package com.compose.weatherforecastapp.domain.repository.location

import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates


interface LocationRepository {
    suspend fun getCoordinates(): Coordinates?
    suspend fun getCityName(): String?
}