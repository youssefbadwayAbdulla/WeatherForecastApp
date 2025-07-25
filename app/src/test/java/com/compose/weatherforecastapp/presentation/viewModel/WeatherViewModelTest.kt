package com.compose.weatherforecastapp.presentation.viewModel

import android.content.Context
import com.compose.weatherforecastapp.common.utils.DataState
import com.compose.weatherforecastapp.common.utils.TemperatureUnit
import com.compose.weatherforecastapp.domain.model.currentWeather.WeatherResponse
import com.compose.weatherforecastapp.domain.usecase.GetWeatherUseCase
import com.compose.weatherforecastapp.presentation.state.WeatherUiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val context: Context = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(getWeatherUseCase, context)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeather emits Loading then Success`() = runTest {
        val response = mockk<WeatherResponse>()
        coEvery { getWeatherUseCase.invoke(any(), any()) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Success(response))
        }

        viewModel.getWeather(10.0, 20.0, "Cairo")
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assert(state is WeatherUiState.Success)
        Assertions.assertEquals("Cairo", (state as WeatherUiState.Success).cityName)
        Assertions.assertEquals(response, state.data)
    }

    @Test
    fun `getWeather emits Loading then Error`() = runTest {
        val error = RuntimeException("Failed to load")
        coEvery { getWeatherUseCase.invoke(any(), any()) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Error(error))
        }

        viewModel.getWeather(10.0, 20.0, "London")
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assert(state is WeatherUiState.Error)
        Assertions.assertEquals("Failed to load", (state as WeatherUiState.Error).message)
    }

    @Test
    fun `temperature unit should update when setTemperatureUnit is called`() = runTest {
        assertEquals(TemperatureUnit.CELSIUS, viewModel.temperatureUnit.value)

        viewModel.setTemperatureUnit(TemperatureUnit.FAHRENHEIT)
        assertEquals(TemperatureUnit.FAHRENHEIT, viewModel.temperatureUnit.value)
    }

}
