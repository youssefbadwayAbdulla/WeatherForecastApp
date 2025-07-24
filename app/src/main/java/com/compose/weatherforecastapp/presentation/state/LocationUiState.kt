package com.compose.weatherforecastapp.presentation.state


data class LocationUiState(
    val isPermissionGranted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val shouldNavigateToWeather: Boolean = false,
    val cityName: String? = null
)
