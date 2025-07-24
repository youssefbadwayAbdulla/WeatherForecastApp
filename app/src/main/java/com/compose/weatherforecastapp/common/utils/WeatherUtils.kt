package com.compose.weatherforecastapp.common.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.model.currentWeather.Main
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.graphics.createBitmap

fun Double.toCelsius(): Double = this - 273.15
fun Double.toFahrenheit(): Double = (this - 273.15) * 9 / 5 + 32

@SuppressLint("DefaultLocale")
fun Double.formatTemp(unit: TemperatureUnit): String {
    return when (unit) {
        TemperatureUnit.CELSIUS -> String.format("%.1f°C", this)
        TemperatureUnit.FAHRENHEIT -> String.format("%.1f°F", this)
    }
}

fun Int.toFormattedDate(): String {
    val date = Date(this.toLong() * 1000)
    return SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(date)
}

fun Bitmap.overlayWeatherData(weather: WeatherResponse, unit: TemperatureUnit): Bitmap {
    val bitmap = createBitmap(width, height, config!!)
    val canvas = Canvas(bitmap)
    canvas.drawBitmap(this, 0f, 0f, null)

    val paint = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        isAntiAlias = true
        setShadowLayer(5f, 2f, 2f, Color.BLACK)
    }

    val temp = weather.main?.let {
        when (unit) {
            TemperatureUnit.CELSIUS -> it.temp?.toCelsius()
            TemperatureUnit.FAHRENHEIT -> it.temp?.toFahrenheit()
        }
    } ?: 0.0

    val text = """
        Location: ${weather.cityName ?: "Unknown"}
        Temp: ${temp.formatTemp(unit)}
        Humidity: ${weather.main?.humidity ?: 0}%
        Wind: ${weather.wind?.speed ?: 0.0} m/s
        Condition: ${weather.weather?.firstOrNull()?.description ?: "N/A"}
    """.trimIndent()

    var y = 100f
    text.split("\n").forEach { line ->
        canvas.drawText(line, 50f, y, paint)
        y += 60f
    }

    return bitmap
}

enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT
}
