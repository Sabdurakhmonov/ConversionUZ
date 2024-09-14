plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "uz.gita.conversionuz"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.gita.conversionuz"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //kirich
    implementation (libs.viewbindingpropertydelegate.noreflection)

    val nav_version = "2.7.7"


    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    //circle img view
    implementation (libs.circleimageview)

    //di hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Chuck
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")


    //RETROFIT
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.converter.gson)

    //shimmer
    implementation(libs.shimmer)
    //lotte
    implementation ("com.airbnb.android:lottie:6.5.2")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
}