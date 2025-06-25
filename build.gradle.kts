plugins {
    // Define plugins used by subprojects with 'apply false' so they inherit versions from settings/catalog
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    // alias(libs.plugins.kotlinJvm) apply false // If adding an app-jvm module
}

allprojects {
    repositories {
        // google()
        // mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}