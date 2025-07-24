package com.compose.weatherforecastapp.presentation.state

import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse


sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(
        val data: WeatherResponse,
        val cityName: String? = null
    ) : WeatherUiState()

    data class Error(val message: String) : WeatherUiState()
}