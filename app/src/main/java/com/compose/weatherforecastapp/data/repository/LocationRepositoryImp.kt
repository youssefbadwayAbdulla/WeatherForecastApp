package com.compose.weatherforecastapp.data.repository

import android.content.Context
import android.location.Geocoder
import com.compose.weatherforecastapp.domain.model.currentWeather.Coordinates
import com.compose.weatherforecastapp.domain.repository.location.LocationProvider
import com.compose.weatherforecastapp.domain.repository.location.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImp @Inject constructor(
    private val context: Context, private val provider: LocationProvider
) : LocationRepository {
    override suspend fun getCoordinates(): Coordinates? {
        return provider.getCurrentLocation().firstOrNull()?.let { location ->
            Coordinates(location.latitude, location.longitude)
        }

    }

    override suspend fun getCityName(): String? {
        val coordinates = getCoordinates() ?: return null
        val geocoder = Geocoder(context, java.util.Locale.getDefault())
        return withContext(Dispatchers.IO) {
            try {
                val addresses = geocoder.getFromLocation(coordinates.latitude?:0.0, coordinates.longitude?:0.0, 1)
                addresses?.firstOrNull()?.locality
            } catch (e: Exception) {
                null
            }
        }
    }
}