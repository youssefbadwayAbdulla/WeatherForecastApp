package com.compose.weatherforecastapp.data

import com.compose.weatherforecastapp.domain.model.currentWeather.Clouds
import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates
import com.compose.weatherforecastapp.domain.model.currentWeather.Main
import com.compose.weatherforecastapp.domain.model.currentWeather.Rain
import com.compose.weatherforecastapp.domain.model.currentWeather.System
import com.compose.weatherforecastapp.domain.model.currentWeather.Weather
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.model.currentWeather.Wind

internal object WeatherHelperFake {
     val latitude = 40.7128
     val longitude = -74.0060
     val cityName = "New York"
     val sampleWeatherResponse = WeatherResponse(
        base = "stations",
        clouds = Clouds(cloudiness = 75),
        cod = 200,
        coordinates = Coordinates(latitude, longitude),
        timestamp = 1635789600,
        cityId = 5128581,
        main = Main(
            temp = 20.0,
            feelsLike = 19.0,
            tempMin = 18.0,
            tempMax = 22.0,
            pressure = 1012,
            humidity = 65
        ),
        cityName =cityName,
        rain = Rain(precipitation = 0.0),
        system = System(country = "US", sunrise = 1635754800, sunset = 1635794400),
        timezone = -14400,
        visibility = 10000,
        weather = listOf(Weather(description = "broken clouds", icon = "04d", id = 803, main = "Clouds")),
        wind = Wind(speed = 5.0, direction = 180, gust = 7.0)
    )
}