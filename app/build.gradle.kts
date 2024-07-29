import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint.gradle)
    id ("kotlin-kapt")
}

android {
    namespace = "dev.borisochieng.autocaretag"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.borisochieng.autocaretag"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    android = true
    ignoreFailures = false
    reporters {
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}

dependencies {
    val room_version = "2.6.1"
    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.compose.navigation)
    implementation(libs.androidx.constraint.layout.compose)

    // gson
    implementation(libs.gson)

    // di
    implementation(libs.koin.android)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // gson
    implementation(libs.gson)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Gson
    implementation (libs.gson)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    kapt("androidx.room:room-compiler:$room_version")
    implementation(libs.androidx.room.ktx)
}
kapt {
    correctErrorTypes = true
    arguments {
        arg ("room.schemaLocation", "$projectDir/schemas".toString())
    }
}