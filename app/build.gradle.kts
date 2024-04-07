plugins {
    id("org.jetbrains.kotlin.kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.giphy_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.giphy_android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.giphy_android.runner.HiltTestRunner"
        testInstrumentationRunnerArguments["testApiKey"] = properties["apiKey"].toString().replace("\"", "")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "api_key", properties["apiKey"].toString())
        }

        debug {
            resValue("string", "api_key", properties["apiKey"].toString())
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/LICENSE.md",
                "/META-INF/LICENSE-notice.md"
            )
        )
    }
}

dependencies {
    val versions = object {
        val coreKtx = "1.12.0"
        val lifecycle = "2.7.0"
        val activityCompose = "1.8.2"
        val composeVersion = "2023.03.00"
        val gson = "2.9.0"
        val retrofit = "2.9.0"
        val hilt = "2.48"
        val coil = "2.0.0-rc02"
        val coilCompose = "2.4.0"
        val navigationCompose = "2.8.0-alpha05"
        val mockk = "1.13.10"
        val espresso = "3.5.1"
    }

    dependencies {
        // AndroidX
        implementation("androidx.core:core-ktx:${versions.coreKtx}")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:${versions.lifecycle}")
        implementation("androidx.activity:activity-compose:${versions.activityCompose}")
        implementation(platform("androidx.compose:compose-bom:${versions.composeVersion}"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        // Gson for JSON parsing
        implementation("com.google.code.gson:gson:${versions.gson}")

        // Retrofit for REST API calls
        implementation("com.squareup.retrofit2:retrofit:${versions.retrofit}")
        implementation("com.squareup.retrofit2:converter-gson:${versions.retrofit}")

        // Hilt for dependency injection
        implementation("com.google.dagger:hilt-android:${versions.hilt}")
        kapt("com.google.dagger:hilt-compiler:${versions.hilt}")

        // Coil for image loading
        implementation("io.coil-kt:coil-gif:${versions.coil}")
        implementation("io.coil-kt:coil-compose:${versions.coilCompose}")

        // Navigation Compose for navigation management
        implementation("androidx.navigation:navigation-compose:${versions.navigationCompose}")

        // Testing dependencies
        androidTestImplementation("com.google.dagger:hilt-android-testing:${versions.hilt}")
        androidTestImplementation("androidx.navigation:navigation-testing:${versions.lifecycle}")
        androidTestImplementation("io.mockk:mockk-android:${versions.mockk}")
        androidTestImplementation("androidx.test.espresso:espresso-core:${versions.espresso}")
        androidTestImplementation(platform("androidx.compose:compose-bom:${versions.composeVersion}"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        kaptAndroidTest("com.google.dagger:hilt-android-compiler:${versions.hilt}")
    }
}