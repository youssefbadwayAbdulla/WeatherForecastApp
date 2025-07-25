package com.compose.weatherforecastapp.domain.usecase

import com.compose.weatherforecastapp.common.utils.DataState
import com.compose.weatherforecastapp.data.WeatherHelperFake
import com.compose.weatherforecastapp.data.WeatherHelperFake.latitude
import com.compose.weatherforecastapp.data.WeatherHelperFake.longitude
import com.compose.weatherforecastapp.domain.model.currentWeather.*
import com.compose.weatherforecastapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlinx.coroutines.ExperimentalCoroutinesApi
/**
 *  - Test Scenarios:
 *
 *     Successful Response:
 *    - Repository emits Loading then Success
 *    - Assert emitted values are [Loading, Success(data)]
 *
 *     Error Response:
 *    - Repository emits Loading then Error (e.g., network error)
 *    - Assert emitted values are [Loading, Error(exception)]
 *
 *    Empty or Null Response:
 *    - Repository emits Loading then Error with NullPointerException
 *    - Assert emitted values are [Loading, Error(exception)]
 *
 *     Invalid Coordinates:
 *    - Repository emits Loading then Error with IllegalArgumentException
 *    - Assert emitted values are [Loading, Error(exception)]
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetWeatherUseCaseTest {

    private lateinit var useCase: GetWeatherUseCase
    private val weatherRepository: WeatherRepository = mockk()


    @BeforeEach
    fun setup() {
        useCase = GetWeatherUseCase(weatherRepository)
    }

    @Test
    fun `when repository returns successful response, emits Loading then Success`() = runTest {

        coEvery { weatherRepository.getWeatherData(latitude, longitude = longitude) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Success(WeatherHelperFake.sampleWeatherResponse))
        }


        val result = useCase(latitude, longitude).toList()


        assertEquals(2, result.size)
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Success(WeatherHelperFake.sampleWeatherResponse), result[1])
    }

    @Test
    fun `when repository returns error, emits Loading then Error`() = runTest {

        val exception = RuntimeException("Network error")
        coEvery { weatherRepository.getWeatherData(latitude, longitude) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Error<WeatherResponse>(exception))
        }


        val result = useCase(latitude, longitude).toList()


        assertEquals(2, result.size)
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Error<WeatherResponse>(exception), result[1])
    }

    @Test
    fun `when repository returns empty response, emits Loading then Error`() = runTest {

        val exception = NullPointerException("Empty response")
        coEvery { weatherRepository.getWeatherData(latitude, longitude) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Error<WeatherResponse>(exception))
        }


        val result = useCase(latitude, longitude).toList()


        assertEquals(2, result.size)
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Error<WeatherResponse>(exception), result[1])
    }

    @Test
    fun `when invalid coordinates are provided, emits Loading then Error`() = runTest {

        val invalidLatitude = 100.0
        val exception = IllegalArgumentException("Invalid coordinates")
        coEvery { weatherRepository.getWeatherData(invalidLatitude, longitude) } returns flow {
            emit(DataState.Loading)
            emit(DataState.Error<WeatherResponse>(exception))
        }


        val result = useCase(invalidLatitude, longitude).toList()


        assertEquals(2, result.size)
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Error<WeatherResponse>(exception), result[1])
    }

    @Test
    fun `when repository is slow, emits Loading first`() = runTest {

        coEvery { weatherRepository.getWeatherData(latitude, longitude) } returns flow {
            emit(DataState.Loading)
        }

        val result = useCase(latitude, longitude).toList()

        assertEquals(1, result.size)
        assertEquals(DataState.Loading, result[0])
    }
}