package com.compose.weatherforecastapp.domain.usecase.location

import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates
import com.compose.weatherforecastapp.domain.repository.location.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend fun getCoordinates(): Coordinates? = repository.getCoordinates()
    suspend fun getCityName(): String? = repository.getCityName()
}