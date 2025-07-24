package com.compose.weatherforecastapp.domain.repository

import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.utils.DataState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Flow<DataState<WeatherResponse>>
}