plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm() // We need a JVM variant if task-persistence-room's JVM target depends on it
    // We can add other targets (js, native) if we want this model to be truly multiplatform later

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib.common)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib.jdk8)
            }
        }
    }
}