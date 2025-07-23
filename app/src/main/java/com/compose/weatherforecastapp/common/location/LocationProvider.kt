package com.compose.weatherforecastapp.common.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun getCurrentLocation(): Flow<Location>

}