package com.compose.weatherforecastapp.utils

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<T>(val error: Throwable) : DataState<T>()
}