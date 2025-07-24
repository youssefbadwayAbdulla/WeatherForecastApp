package com.compose.weatherforecastapp.di

import com.compose.weatherforecastapp.common.location.GpsLocationProvider
import com.compose.weatherforecastapp.common.location.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationProviderModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        gpsLocationProvider: GpsLocationProvider
    ): LocationProvider


}
