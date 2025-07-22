# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##############################################
# Hilt / Dagger
##############################################
# Hilt/Dagger
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }

# Keep generated Hilt code
-keepclassmembers class * {
    @dagger.hilt.android.internal.lifecycle.HiltViewModelMap <methods>;
}
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <methods>;
}
-keep class * extends androidx.lifecycle.ViewModel

# Hilt warning suppression
-dontwarn dagger.hilt.**

##############################################
# Retrofit & Gson
##############################################
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

##############################################
# Coroutines
##############################################
-dontwarn kotlinx.coroutines.**

##############################################
# Coil
##############################################
# Coil image loading
-dontwarn coil.**
-keep class coil.** { *; }
-keep class coil.request.** { *; }

##############################################
# CameraX
##############################################
# CameraX
-dontwarn androidx.camera.**
-keep class androidx.camera.** { *; }

##############################################
# Google Play Location Services
##############################################
-dontwarn com.google.android.gms.location.**
-keep class com.google.android.gms.location.** { *; }

##############################################
# Jetpack Compose (UI, Tooling)
##############################################
# Compose Compiler
-dontwarn kotlin.**  # safe default
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }

##############################################
# Kotlinx Serialization
##############################################
-dontwarn kotlinx.serialization.**
-keep class kotlinx.serialization.** { *; }

##############################################
# General rules for reflection (safe fallback)
##############################################
# For generic reflection
-keepclassmembers class * {
    *;
}
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
