plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.freddy.proyectoqrasistencia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.freddy.proyectoqrasistencia"
        minSdk = 33
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Ktor core client
    implementation("io.ktor:ktor-client-core:2.3.7")

    // Choose the appropriate engine (e.g., for Android)
    implementation("io.ktor:ktor-client-android:2.3.7")

    // Optional: JSON serialization
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")

        // AndroidX Core
    implementation("androidx.core:core-ktx:1.12.0")

        // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")

    val compose_version = "1.6.3"
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")

        // Optional but recommended for Compose debugging
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
        // Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("io.ktor:ktor-client-java:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("io.ktor:ktor-client-cio:2.3.7")
    implementation("com.auth0:java-jwt:4.4.0")

    }

