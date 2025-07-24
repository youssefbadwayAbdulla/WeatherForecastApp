package com.compose.weatherforecastapp.data.repository

import com.compose.weatherforecastapp.data.remote.WeatherService
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.repository.WeatherRepository
import com.compose.weatherforecastapp.common.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Flow<DataState<WeatherResponse>> = flow {
        emit(DataState.Loading)
        runCatching {
            weatherService.getCurrentWeather(latitude, longitude)
        }.onSuccess { response ->
            response.body()?.let {
                emit(DataState.Success(it))
            }
        }.onFailure { error ->
            emit(DataState.Error(error))
        }


    }

}