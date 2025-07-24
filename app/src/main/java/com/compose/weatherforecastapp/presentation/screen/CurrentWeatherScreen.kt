package com.compose.weatherforecastapp.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.weatherforecastapp.common.extentions.openImageInGallery
import com.compose.weatherforecastapp.common.extentions.saveToStorage
import com.compose.weatherforecastapp.common.uiHelper.rememberCameraLauncher
import com.compose.weatherforecastapp.common.utils.overlayWeatherData
import com.compose.weatherforecastapp.presentation.state.WeatherUiState
import com.compose.weatherforecastapp.presentation.viewModel.LocationViewModel
import com.compose.weatherforecastapp.presentation.viewModel.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun CurrentWeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val weatherState by weatherViewModel.weatherState.collectAsState()
    val temperatureUnit by weatherViewModel.temperatureUnit.collectAsState()
    val imageHistory by weatherViewModel.imageHistory.collectAsState()
    val locationState by locationViewModel.uiState.collectAsState()

    val launchCamera = rememberCameraLauncher { bitmap ->
        bitmap?.let {
            (weatherState as? WeatherUiState.Success)?.let { weather ->
                coroutineScope.launch {
                    try {
                        val overlaid = bitmap.overlayWeatherData(weather.data, temperatureUnit)
                        val file = overlaid.saveToStorage(context)
                        if (file != null) {
                            weatherViewModel.saveImageToHistory(file)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        locationViewModel.onPermissionResult(granted)
    }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            locationViewModel.onPermissionResult(true)
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(locationState.latitude, locationState.longitude) {
        if (locationState.latitude != null && locationState.longitude != null) {
            weatherViewModel.getWeather(
                latitude = locationState.latitude ?: 0.0,
                longitude = locationState.longitude ?: 0.0,
                cityName = locationState.cityName ?: "Current Location"
            )
        }
    }

    if (locationState.latitude == null || locationState.longitude == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        return
    }

    WeatherScreenContent(
        modifier = modifier,
        weatherState = weatherState,
        temperatureUnit = temperatureUnit,
        imageHistory = imageHistory,
        onUnitSelected = weatherViewModel::setTemperatureUnit,
        onCapture = launchCamera,
        onImageClick = { file -> openImageInGallery(context, file) }
    )
}


