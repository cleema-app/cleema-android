/*
 * Created by Kumpels and Friends on 2023-01-16
 * Copyright © 2023 Kumpels and Friends. All rights reserved.
 */

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.*

plugins {
    id("cleema.android.application")
    id("cleema.android.application.compose")
    id("cleema.android.hilt")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

secrets {
    propertiesFileName = "secret.properties"
    defaultPropertiesFileName = "secret.defaults.properties"
    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}

android {
    namespace = "de.cleema.android"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "de.cleema.android"
        versionCode = gitBuildNumber()
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        val defaultStorePath = System.getProperty("user.home") + "/.android/debug.keystore"
        getByName("debug") {
            storeFile = file(defaultStorePath)
        }

        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as? String ?: ""
            keyPassword = keystoreProperties["keyPassword"] as? String ?: ""
            storeFile = file(keystoreProperties["storeFile"] as? String ?: defaultStorePath)
            storePassword = keystoreProperties["storePassword"] as? String ?: ""
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        val debug by getting {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:styling"))
    implementation(project(":core:models"))
    implementation(project(":core:components"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.arrow.core)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.datetime)
    implementation(libs.konfetti)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)

    testImplementation(libs.hilt.android.testing)
    // AndroidX Test - JVM testing
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)
    // AndroidX Test - Instrumented testing
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    androidTestImplementation(libs.androidx.navigation.testing)
//    androidTestImplementation(libs.accompanist.testharness)
//    androidTestImplementation(kotlin("test"))
//    debugImplementation(libs.androidx.compose.ui.testManifest)
}

fun gitBuildNumber(): Int {
    val stdout = ByteArrayOutputStream()
    rootProject.exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim().toInt()
}

kapt {
    correctErrorTypes = true
}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}
