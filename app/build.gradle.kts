plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.theatersData))
    implementation(project(Modules.theatersDomain))
    implementation(project(Modules.theatersPresentation))
    implementation(project(Modules.topData))
    implementation(project(Modules.topDomain))
    implementation(project(Modules.topPresentation))
    implementation(project(Modules.popularData))
    implementation(project(Modules.popularDomain))
    implementation(project(Modules.popularPresentation))
    implementation(project(Modules.detailsData))
    implementation(project(Modules.detailsDomain))
    implementation(project(Modules.detailsPresentation))
    implementation(project(Modules.searchData))
    implementation(project(Modules.searchDomain))
    implementation(project(Modules.searchPresentation))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Android.material)
    implementation(Android.constraintLayout)

    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.lifecycleViewModelKtx)
    implementation(AndroidX.lifecycleLivedataKtx)

    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationUiKtx)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.gson)
    implementation(Retrofit.gsonConverter)

    implementation(Coroutines.core)
    implementation(Coroutines.android)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Glide.glide)
    kapt(Glide.glideCompiler)

    implementation(Refresh.swipeRefresh)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
}