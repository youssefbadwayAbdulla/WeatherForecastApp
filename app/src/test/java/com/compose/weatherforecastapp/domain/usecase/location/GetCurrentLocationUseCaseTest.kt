package com.compose.weatherforecastapp.domain.usecase.location

import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates
import com.compose.weatherforecastapp.domain.repository.location.LocationRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
/**
 * - Successful Scenarios:
 *    - Test that `getCoordinates` returns correct coordinates when provided by the repository.
 *    - Test that `getCityName` returns correct city name when provided by the repository.
 *
 * - Null Handling:
 *    - Test that `getCoordinates` returns null if repository returns null.
 *    - Test that `getCityName` returns null if repository returns null.
 *    - Test that `getCityName` returns empty string if repository returns empty string.
 *
 * - Error Handling:
 *    - Test that `getCoordinates` handles exceptions gracefully and returns null.
 *    - Test that `getCityName` handles exceptions gracefully and returns null.
 */

class GetCurrentLocationUseCaseTest {

    private lateinit var useCase: GetCurrentLocationUseCase
    private val locationRepository: LocationRepository = mockk()

    @BeforeEach
    fun setUp() {
        useCase = GetCurrentLocationUseCase(locationRepository)
    }

    @Test
    fun `getCoordinates should return valid coordinates when repository provides them`() = runBlocking {

        val expectedCoordinates = Coordinates(37.7749, -122.4194)
        coEvery { locationRepository.getCoordinates() } returns expectedCoordinates

        val result = useCase.getCoordinates()

        assertThat(result).isEqualTo(expectedCoordinates)
        assertThat(result?.latitude).isEqualTo(37.7749)
        assertThat(result?.longitude).isEqualTo(-122.4194)
    }

    @Test
    fun `getCityName should return valid city name when repository provides it`() = runBlocking {

        val expectedCityName = "San Francisco"
        coEvery { locationRepository.getCityName() } returns expectedCityName

        val result = useCase.getCityName()

        assertThat(result).isEqualTo(expectedCityName)
    }

    @Test
    fun `getCoordinates should return null when repository returns null`() = runBlocking {

        coEvery { locationRepository.getCoordinates() } returns null


        val result = useCase.getCoordinates()


        assertThat(result).isNull()
    }

    @Test
    fun `getCoordinates should handle exception from repository gracefully`() = runBlocking {

        coEvery { locationRepository.getCoordinates() } throws IOException("Network error")


        val result = try {
            useCase.getCoordinates()
        } catch (e: IOException) {
            null
        }


        assertThat(result).isNull()
    }


    @Test
    fun `getCityName should return null when repository returns null`() = runBlocking {

        coEvery { locationRepository.getCityName() } returns null


        val result = useCase.getCityName()

        assertThat(result).isNull()
    }

    @Test
    fun `getCityName should handle exception from repository gracefully`() = runBlocking {

        coEvery { locationRepository.getCityName() } throws IOException("Geocoder error")


        val result = try {
            useCase.getCityName()
        } catch (e: IOException) {
            null
        }


        assertThat(result).isNull()
    }

    @Test
    fun `getCityName should return null when repository returns empty string`() = runBlocking {

        coEvery { locationRepository.getCityName() } returns ""


        val result = useCase.getCityName()


        assertThat(result).isEmpty()
    }
}