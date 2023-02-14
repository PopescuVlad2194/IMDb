apply {
    from("$rootDir/base-module-ui.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.topDomain))

    "implementation"(Glide.glide)
    "implementation"(Glide.glideCompiler)
}