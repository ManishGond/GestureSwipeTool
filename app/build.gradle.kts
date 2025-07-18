plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.maxintech.gestureswipetool"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.maxintech.gestureswipetool"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dependency Injection with HILT
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Hilt with Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Added Dagger Hilt Compiler
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.compiler)
    ksp(libs.dagger.compiler)

    // TensorFlow
    implementation (libs.tensorflow.lite)

    // CameraX
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)

    // Lifecycle + ViewModel
    implementation (libs.androidx.lifecycle.runtime.ktx.v270)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Dependency Injection with HILT
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Hilt with Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)
    implementation (libs.kotlinx.coroutines.android)
    // Added Dagger Hilt Compiler
    implementation(libs.dagger.compiler)
    ksp(libs.dagger.compiler)

    implementation (libs.androidx.runtime.livedata)

    implementation ("com.google.mediapipe:tasks-vision:0.10.14")

    implementation ("androidx.lifecycle:lifecycle-service:2.7.0")

}