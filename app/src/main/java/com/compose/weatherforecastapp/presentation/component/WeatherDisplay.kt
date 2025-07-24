package com.compose.weatherforecastapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.compose.weatherforecastapp.common.extentions.preview.BasePreview
import com.compose.weatherforecastapp.common.extentions.preview.PreviewMultiDevices
import com.compose.weatherforecastapp.common.utils.TemperatureUnit
import com.compose.weatherforecastapp.common.utils.formatTemp
import com.compose.weatherforecastapp.common.utils.toCelsius
import com.compose.weatherforecastapp.common.utils.toFahrenheit
import com.compose.weatherforecastapp.common.utils.toFormattedDate
import com.compose.weatherforecastapp.domain.model.currentWeather.Main
import com.compose.weatherforecastapp.domain.model.currentWeather.Weather
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.model.currentWeather.Wind

@Composable
internal fun WeatherDisplay(
    weather: WeatherResponse,
    temperatureUnit: TemperatureUnit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = weather.cityName ?: "Unknown Location",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                weather.timestamp?.let {
                    Text(
                        text = it.toFormattedDate(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                WeatherIcon(icon = weather.weather?.firstOrNull()?.icon)

                weather.main?.temp?.let { temp ->
                    val convertedTemp = when (temperatureUnit) {
                        TemperatureUnit.CELSIUS -> temp.toCelsius()
                        TemperatureUnit.FAHRENHEIT -> temp.toFahrenheit()
                    }
                    Text(
                        text = convertedTemp.formatTemp(temperatureUnit),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                WeatherInfoRow(label = "Humidity", value = "${weather.main?.humidity ?: "-"}%")
                WeatherInfoRow(label = "Wind", value = "${weather.wind?.speed ?: "-"} m/s")
                WeatherInfoRow(
                    label = "Condition",
                    value = weather.weather?.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "-"
                )
            }
        }
    }
}


@Composable
private fun WeatherIcon(icon: String?) {
    icon?.let {
        AsyncImage(
            model = "https://openweathermap.org/img/wn/${it}@2x.png",
            contentDescription = "Weather icon",
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
private fun WeatherInfoRow(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@PreviewMultiDevices
@Composable
fun WeatherDisplayPreview() {
    BasePreview {
        val fakeWeather = WeatherResponse(
            cityName = "Cairo",
            timestamp = (System.currentTimeMillis() / 1000).toInt(),
            weather = listOf(
               Weather(
                    main = "Clouds",
                    description = "broken clouds",
                    icon = "04d"
                )
            ),
            main = Main(
                temp = 303.15,
                humidity = 65
            ),
            wind = Wind(speed = 5.0)
        )

        MaterialTheme {
            WeatherDisplay(weather = fakeWeather, temperatureUnit = TemperatureUnit.CELSIUS)
        }
    }

}

