package com.compose.weatherforecastapp.data.remote

import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = "803f3dd03df8880d609d8835921d2fe2"
    ): Response<WeatherResponse>
}