import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 29
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "com.duzhaokun123.toasticon"
        minSdk = 22
        targetSdk = 29
        versionCode = 4
        versionName = "0.1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        if (rootProject.file("local.properties").exists()) {
            val properties = Properties()
            properties.load(rootProject.file("local.properties").inputStream())
            register("release") {
                storeFile(file("../ReleaseKey.jks"))
                storePassword = properties.getProperty("REL_KEY")
                keyAlias = "key0"
                keyPassword = properties.getProperty("REL_KEY")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isZipAlignEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    // xposed
    compileOnly("de.robv.android.xposed:api:82")
    implementation("com.github.ikws4:xposed-ktx:1.0")

    // remotePreference
    implementation("com.crossbowffs.remotepreferences:remotepreferences:0.8")
}