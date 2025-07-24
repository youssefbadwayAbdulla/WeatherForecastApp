package com.compose.weatherforecastapp.presentation.state


data class LocationUiState(
    val isPermissionGranted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    val cityName: String? = null
)
