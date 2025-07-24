package com.compose.weatherforecastapp.common.extentions.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

// Light/Dark mode previews for all devices

@Preview(
    name = "Phone",
    group = "Devices",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Preview(
    name = "Phone - Arabic",
    group = "Devices",
    locale = "ar",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Preview(
    name = "Foldable",
    group = "Devices",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"
)
@Preview(
    name = "Tablet",
    group = "Devices",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"
)
@PreviewLightDark
annotation class PreviewMultiDevices
