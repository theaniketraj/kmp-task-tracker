plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp) // Apply KSP plugin for Room code generation
}

kotlin {
    jvm { // This module provides a JVM implementation using Room
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }
    // No other targets for this specific persistence module for now

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Depends on the common models from :core-model
                api(project(":core-model"))
                // Room annotations are needed in common if entities are defined here
                api(libs.androidx.room.common)
                api(libs.kotlinx.coroutines.core) // For suspend functions in DAO
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib.jdk8)
                // Room runtime and KTX for JVM
                api(libs.androidx.room.runtime)
                implementation("androidx.room:room-sqlite-bundled:$roomVersion")
                api(libs.androidx.room.ktx)
                // SQLite framework and JDBC driver for JVM
                api(libs.androidx.sqlite.framework)
                implementation(libs.sqlite.jdbc)

                // KSP processor for Room on the JVM target
                kspJvm(libs.androidx.room.compiler)
            }
        }
    }
}

// KSP arguments for Room
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}