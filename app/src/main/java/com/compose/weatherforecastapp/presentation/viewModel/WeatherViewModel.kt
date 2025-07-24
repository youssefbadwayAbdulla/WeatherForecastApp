package com.compose.weatherforecastapp.presentation.viewModel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.weatherforecastapp.common.extentions.getCurrentLocation
import com.compose.weatherforecastapp.common.extentions.hasLocationPermission
import com.compose.weatherforecastapp.common.utils.DataState
import com.compose.weatherforecastapp.common.utils.TemperatureUnit
import com.compose.weatherforecastapp.domain.usecase.GetWeatherUseCase
import com.compose.weatherforecastapp.presentation.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    private val _temperatureUnit = MutableStateFlow(TemperatureUnit.CELSIUS)
    val temperatureUnit: StateFlow<TemperatureUnit> = _temperatureUnit

    private val _imageHistory = MutableStateFlow<List<File>>(emptyList())
    val imageHistory: StateFlow<List<File>> = _imageHistory

    init {
        loadImageHistory()
    }

    fun getWeather(latitude: Double, longitude: Double, cityName: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                getWeatherUseCase.invoke(latitude, longitude)
                    .collectLatest { dataState ->
                        _weatherState.value = when (dataState) {
                            is DataState.Loading -> WeatherUiState.Loading
                            is DataState.Success -> WeatherUiState.Success(dataState.data, cityName)
                            is DataState.Error -> WeatherUiState.Error(
                                dataState.error.message ?: "Unknown error"
                            )
                        }
                    }
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error("Failed to fetch weather: ${e.message}")
            }
        }
    }

    fun setTemperatureUnit(unit: TemperatureUnit) {
        _temperatureUnit.value = unit
    }

    fun saveImageToHistory(file: File) {
        _imageHistory.value = _imageHistory.value + file
    }

    private fun loadImageHistory() {
        viewModelScope.launch {
            val directory = context.getExternalFilesDir(null)
            val imageFiles = directory?.listFiles { _, name -> name.endsWith(".png") }?.toList() ?: emptyList()
            _imageHistory.value = imageFiles.sortedByDescending { it.lastModified() }
        }
    }
}
