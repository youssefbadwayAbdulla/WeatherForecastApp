package com.compose.weatherforecastapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.weatherforecastapp.domain.usecase.GetWeatherUseCase
import com.compose.weatherforecastapp.presentation.state.WeatherUiState
import com.compose.weatherforecastapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    fun getWeather(latitude: Double, longitude: Double, cityName: String) {
        viewModelScope.launch {
            getWeatherUseCase.invoke(latitude, longitude)
                .collectLatest { dataState ->
                    _weatherState.value = when (dataState) {
                        is DataState.Loading -> WeatherUiState.Loading
                        is DataState.Success -> WeatherUiState.Success(dataState.data, cityName)
                        is DataState.Error -> WeatherUiState.Error(dataState.error.message ?: "Unknown error")
                    }
                }
        }
    }
}
