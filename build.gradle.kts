
buildscript {
    dependencies {
        classpath(Build.safeArgsNavigationGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.secretsGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
    }
}

plugins {
    id("com.android.application") version Build.androidApplicationPluginVersion apply false
    id("com.android.library") version Build.androidLibraryPluginVersion apply false
    id("org.jetbrains.kotlin.android") version Kotlin.version apply false
    id("org.jetbrains.kotlin.jvm") version Kotlin.jvmVersion apply false
}