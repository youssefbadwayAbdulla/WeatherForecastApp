package com.compose.weatherforecastapp.presentation.component

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription

@Composable
fun CaptureImageButton(
    onCapture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onCapture,
        modifier = modifier.semantics { contentDescription = "Capture Weather Image Button" }
    ) {
        Text(
            text = "Capture Image",
            style = MaterialTheme.typography.labelLarge
        )
    }
}