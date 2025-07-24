package com.compose.weatherforecastapp.common.extentions.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.weatherforecastapp.presentation.theme.WeatherForecastAppTheme

@Composable
fun BasePreview(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    WeatherForecastAppTheme {
        Surface(modifier = modifier) {
            content()
        }
    }
}