import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.ByteArrayOutputStream

fun Project.gitCommitCount(): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim().toInt()
}

plugins {
    alias(libs.plugins.kapt)
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gms.google.services)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinx.serialization)
}

val majorVersion = 2
val minorVersion = 0
val patchVersion = 0

android {
    namespace = "ru.aip.intern"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.aip.intern"
        minSdk = 28
        targetSdk = 34
        versionCode = gitCommitCount()
        versionName = "$majorVersion.$minorVersion.$patchVersion"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("keystore/ru.unilms.jks")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as BaseVariantOutputImpl }
                    .forEach { output ->
                        val outputFileName =
                            "${
                                rootProject.name.replace(
                                    " ",
                                    ""
                                )
                            }.apk"
                        output.outputFileName = outputFileName
                    }
            }
        }

        getByName("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.kizitonwose.calendar.compose)

    implementation(libs.ktor.client.core) // Базовая клиентская библиотека
    implementation(libs.ktor.client.android) // Плагин для работы в Android
    implementation(libs.ktor.client.content.negotiation) // Плагины для парсинга JSON в модели и обратно
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging) // Плагин для логгирования

    implementation(libs.slf4j.api)
    implementation(libs.tony19.logback.android)  // Драйвер логгирования

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

task("printVersionName") {
    val versionName = "${
        android.defaultConfig.versionName!!.replace(
            ".",
            ""
        )
    }-${gitCommitCount()}"
    project.extensions.extraProperties["fullVersion"] = versionName
    println(versionName)
}