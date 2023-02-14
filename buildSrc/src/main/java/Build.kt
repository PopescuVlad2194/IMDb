object Build {
    const val androidApplicationPluginVersion = "7.3.1"
    const val androidLibraryPluginVersion = "7.3.1"

    private const val hiltAndroidGradlePluginVersion = "2.42"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val crashlyticsGradlePluginVersion = "2.9.2"
    const val crashlyticsGradlePlugin = "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsGradlePluginVersion"

    private const val performanceGradlePluginVersion = "1.4.2"
    const val performanceGradlePlugin = "com.google.firebase:perf-plugin:$performanceGradlePluginVersion"

    private const val googleServicesGradlePluginVersion = "4.3.14"
    const val googleServicesGradlePlugin = "com.google.gms:google-services:$googleServicesGradlePluginVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val secretsGradlePluginVersion = "2.0.1"
    const val secretsGradlePlugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:$secretsGradlePluginVersion"

    const val safeArgsNavigationGradlePlugin = "androidx.navigation.safeargs.kotlin:androidx.navigation.safeargs.kotlin.gradle.plugin:${Navigation.navigationVersion}"
}