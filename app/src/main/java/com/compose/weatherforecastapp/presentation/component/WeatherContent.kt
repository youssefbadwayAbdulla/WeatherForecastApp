package com.compose.weatherforecastapp.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.weatherforecastapp.common.uiHelper.ErrorMessage
import com.compose.weatherforecastapp.common.uiHelper.LoadingIndicator
import com.compose.weatherforecastapp.common.utils.TemperatureUnit
import com.compose.weatherforecastapp.presentation.state.WeatherUiState

@Composable
fun WeatherContent(
    weatherState: WeatherUiState,
    temperatureUnit: TemperatureUnit,
    modifier: Modifier = Modifier
) {
    when (weatherState) {
        is WeatherUiState.Loading -> LoadingIndicator()
        is WeatherUiState.Success -> {
            WeatherDisplay(
                weather = weatherState.data,
                temperatureUnit = temperatureUnit,
            )
        }
        is WeatherUiState.Error -> ErrorMessage(message = weatherState.message,)
    }
}