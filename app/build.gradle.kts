val majorVersion = 1
val minorVersion = 0
val patchVersion = 0

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.sarhanm.versioner") version "4.1.9"
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "ru.unilms"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.unilms"
        minSdk = 28
        targetSdk = 34
        versionCode = majorVersion * 10000 + minorVersion * 100 + patchVersion
        versionName = "$majorVersion.$minorVersion.$patchVersion"

        vectorDrawables.useSupportLibrary = true
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
            applicationVariants.all {
                val variant = this
                val flavorName = variant.productFlavors.first().name
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val outputFileName =
                            "${rootProject.name}-${flavorName}-${defaultConfig.versionName}.apk"
                        output.outputFileName = outputFileName
                    }
            }
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug (${gitdata.branch}@${gitdata.commit})"
        }
    }

    flavorDimensions += listOf("role")

    productFlavors {
        create("admin") {
            dimension = "role"
            applicationIdSuffix = ".admin"
            resValue("string", "app_name", "@string/app_name_admin")
        }
        create("tutor") {
            dimension = "role"
            applicationIdSuffix = ".tutor"
            resValue("string", "app_name", "@string/app_name_tutor")
        }
        create("student") {
            dimension = "role"
            applicationIdSuffix = ".student"
            resValue("string", "app_name", "@string/app_name_student")
        }
    }

    sourceSets {
        named("tutor") {
            kotlin.srcDirs("src/tutorAndStudent")
        }
        named("student") {
            kotlin.srcDirs("src/tutorAndStudent")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val ktorVersion = "2.3.4"
    val hiltVersion = "2.48.1"
    val richTextVersion = "0.17.0"

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.12.0")


    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Ktor (http client)
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")


    // Forms
    implementation("com.github.benjamin-luescher:compose-form:0.2.3")

    // DI (Hilt)
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Images
    implementation("io.coil-kt:coil-compose:2.5.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Calendar
    implementation("com.kizitonwose.calendar:compose:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    // Debug stuff
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // RichText
    implementation("com.halilibo.compose-richtext:richtext-ui-material3:${richTextVersion}")
    implementation("com.halilibo.compose-richtext:richtext-commonmark:${richTextVersion}")

    // Downloading files
    implementation("androidx.work:work-runtime-ktx:2.8.1")

}

task("printVersionName") {
    println(android.defaultConfig.versionName)
}