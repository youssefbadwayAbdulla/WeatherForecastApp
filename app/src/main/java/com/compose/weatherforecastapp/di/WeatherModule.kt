package com.compose.weatherforecastapp.di

import android.content.Context
import com.compose.weatherforecastapp.common.location.GpsLocationProvider
import com.compose.weatherforecastapp.common.location.LocationProvider
import com.compose.weatherforecastapp.data.remote.WeatherService
import com.compose.weatherforecastapp.data.repository.LocationRepositoryImp
import com.compose.weatherforecastapp.data.repository.WeatherRepositoryImpl
import com.compose.weatherforecastapp.domain.repository.WeatherRepository
import com.compose.weatherforecastapp.domain.repository.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherRepo(apiService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLocationRepo(
        @ApplicationContext context: Context,
        provider: LocationProvider
    ): LocationRepository {
        return LocationRepositoryImp(context, provider)
    }
}