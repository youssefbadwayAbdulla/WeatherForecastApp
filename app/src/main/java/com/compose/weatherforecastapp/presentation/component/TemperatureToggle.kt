package com.compose.weatherforecastapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.compose.weatherforecastapp.common.utils.TemperatureUnit

@Composable
fun TemperatureToggle(
    temperatureUnit: TemperatureUnit,
    onUnitSelected: (TemperatureUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Temperature Unit Selector" },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onUnitSelected(TemperatureUnit.CELSIUS) },
            enabled = temperatureUnit != TemperatureUnit.CELSIUS,
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        ) {
            Text(
                text = "Celsius",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Button(
            onClick = { onUnitSelected(TemperatureUnit.FAHRENHEIT) },
            enabled = temperatureUnit != TemperatureUnit.FAHRENHEIT,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        ) {
            Text(
                text = "Fahrenheit",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}