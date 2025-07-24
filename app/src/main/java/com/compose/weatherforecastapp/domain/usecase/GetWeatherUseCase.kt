package com.compose.weatherforecastapp.domain.usecase

import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.repository.WeatherRepository
import com.compose.weatherforecastapp.common.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<DataState<WeatherResponse>> =
        repository.getWeatherData(latitude, longitude)
}