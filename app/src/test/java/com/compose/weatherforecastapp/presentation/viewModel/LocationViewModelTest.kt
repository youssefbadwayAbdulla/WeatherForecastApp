package com.compose.weatherforecastapp.presentation.viewModel

import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates
import com.compose.weatherforecastapp.domain.usecase.location.GetCurrentLocationUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

/**
 * - Scenarios:
 *
 *      Permission Granted:
 *    - Should call getCoordinates() and getCityName() successfully.
 *    - UI state should reflect location info (lat, lng, city name).
 *
 *      Permission Denied:
 *    - Should not fetch location.
 *    - UI state should show error: "Location permission is required".
 *
 *     ️ Coordinates Null:
 *    - If getCoordinates() returns null, should set "Location not available" error.
 *
 *       Exception from Repository:
 *    - If getCoordinates() throws (e.g. IOException), should set appropriate error message.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    private lateinit var viewModel: LocationViewModel
    private val getCurrentLocation: GetCurrentLocationUseCase = mockk()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = LocationViewModel(getCurrentLocation)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onPermissionResult with granted permission should fetch location successfully`() = runTest {

        val coordinates = Coordinates(37.7749, -122.4194)
        val cityName = "San Francisco"
        coEvery { getCurrentLocation.getCoordinates() } returns coordinates
        coEvery { getCurrentLocation.getCityName() } returns cityName

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertThat(uiState.isPermissionGranted).isTrue()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.latitude).isEqualTo(37.7749)
        assertThat(uiState.longitude).isEqualTo(-122.4194)
        assertThat(uiState.cityName).isEqualTo(cityName)
        assertThat(uiState.error).isNull()
    }

    @Test
    fun `onPermissionResult with denied permission should set error state`() = runTest {

        viewModel.onPermissionResult(false)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertThat(uiState.isPermissionGranted).isFalse()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.error).isEqualTo("Location permission is required")
        assertThat(uiState.latitude).isNull()
        assertThat(uiState.longitude).isNull()
        assertThat(uiState.cityName).isNull()
    }

    @Test
    fun `getLocation with null coordinates should set error state`() = runTest {

        coEvery { getCurrentLocation.getCoordinates() } returns null

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.error).isEqualTo("Location not available")
        assertThat(uiState.latitude).isNull()
        assertThat(uiState.longitude).isNull()
        assertThat(uiState.cityName).isNull()
    }

    @Test
    fun `getLocation with exception should set error state`() = runTest {

        coEvery { getCurrentLocation.getCoordinates() } throws IOException("Network error")

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.error).isEqualTo("Network error")
        assertThat(uiState.latitude).isNull()
        assertThat(uiState.longitude).isNull()
        assertThat(uiState.cityName).isNull()
    }
}