enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    // Versions for plugins are now primarily sourced from libs.versions.toml via alias()
    // So this plugins block inside pluginManagement can often be minimal or empty if all
    // plugin versions are in the catalog and applied with alias() in root build.gradle.kts
    // For clarity, explicitly defining them here with 'apply false' is also fine.
    plugins {
        // id("org.jetbrains.kotlin.multiplatform") version "1.9.23" apply false
        // id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
        id("org.jetbrains.kotlin.multiplatform") version "1.9.23" apply false
        id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
        // id("org.jetbrains.kotlin.jvm") version libs.versions.kotlin.get() apply false // If needed
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "simple-kmp-room-test"
include(":core-model")
include(":task-persistence-room")
// include(":app-jvm") // Later, if we add it