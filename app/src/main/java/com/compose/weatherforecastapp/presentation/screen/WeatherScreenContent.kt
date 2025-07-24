package com.compose.weatherforecastapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.weatherforecastapp.common.extentions.preview.BasePreview
import com.compose.weatherforecastapp.common.extentions.preview.PreviewMultiDevices
import com.compose.weatherforecastapp.common.utils.TemperatureUnit
import com.compose.weatherforecastapp.domain.model.currentWeather.Main
import com.compose.weatherforecastapp.domain.model.currentWeather.Weather
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.model.currentWeather.Wind
import com.compose.weatherforecastapp.presentation.component.CaptureImageButton
import com.compose.weatherforecastapp.presentation.component.EmptyHistoryMessage
import com.compose.weatherforecastapp.presentation.component.ImageHistoryItem
import com.compose.weatherforecastapp.presentation.component.TemperatureToggle
import com.compose.weatherforecastapp.presentation.component.WeatherContent
import com.compose.weatherforecastapp.presentation.component.WeatherTitle
import com.compose.weatherforecastapp.presentation.state.WeatherUiState


@Composable
internal fun WeatherScreenContent(
    modifier: Modifier,
    weatherState: WeatherUiState,
    temperatureUnit: TemperatureUnit,
    imageHistory: List<java.io.File>,
    onUnitSelected: (TemperatureUnit) -> Unit,
    onCapture: () -> Unit,
    onImageClick: (java.io.File) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            WeatherTitle(
                text = "Current Weather",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            TemperatureToggle(
                temperatureUnit = temperatureUnit,
                onUnitSelected = onUnitSelected,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CaptureImageButton(
                onCapture = onCapture,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            WeatherContent(
                weatherState = weatherState,
                temperatureUnit = temperatureUnit
            )
        }

        item {
            if (imageHistory.isEmpty()) {
                EmptyHistoryMessage()
            }
        }

        items(imageHistory, key = { it.absolutePath }) { imageFile ->
            ImageHistoryItem(
                imageFile = imageFile,
                onClick = { onImageClick(imageFile) },
            )
        }
    }
    Spacer(Modifier.padding(bottom = 12.dp))
}

@PreviewMultiDevices
@Composable
private fun CurrentWeatherScreenPreview() {
    BasePreview {
        WeatherScreenContent(
            modifier = Modifier,
            weatherState = WeatherUiState.Success(
                data = WeatherResponse(
                    cityName = "Cairo",
                    timestamp = System.currentTimeMillis().toInt(),
                    main = Main(temp = 298.15, humidity = 60),
                    weather = listOf(Weather(icon = "10d", description = "clear sky")),
                    wind = Wind(speed = 3.5)
                )
            ),
            temperatureUnit = TemperatureUnit.CELSIUS,
            imageHistory = listOf(),
            onUnitSelected = {},
            onCapture = {},
            onImageClick = {}
        )
    }
}
