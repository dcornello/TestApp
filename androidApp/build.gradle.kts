plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.treatwell.testkmm.testapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.treatwell.testkmm.testapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val koin_version = "3.2.0"
    val koin_android_version = "3.2.0"
    val koin_android_compose_version = "3.2.0"
    val compose_material_version = "1.4.3"

    implementation(project(":shared"))
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_android_compose_version")

    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("androidx.compose.ui:ui:${compose_material_version}")
    implementation("androidx.compose.ui:ui-tooling:${compose_material_version}")
    implementation("androidx.compose.ui:ui-tooling-preview:${compose_material_version}")
    implementation("androidx.compose.foundation:foundation:${compose_material_version}")
    implementation("androidx.compose.material:material:${compose_material_version}")
    implementation("androidx.activity:activity-compose:1.6.1")
}
apply(plugin = "com.google.gms.google-services")