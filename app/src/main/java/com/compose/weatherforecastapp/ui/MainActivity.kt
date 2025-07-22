package com.compose.weatherforecastapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.compose.weatherforecastapp.ui.theme.WeatherForecastAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastAppTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}