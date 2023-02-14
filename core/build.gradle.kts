apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
    "implementation"(Retrofit.gson)
    "implementation"(Retrofit.gsonConverter)

    "implementation"(Navigation.navigationFragmentKtx)
    "implementation"(Navigation.navigationUiKtx)
}