plugins {
    alias(libs.plugins.gradle)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "mx.irisoft.pruebatecniva"
    compileSdk = 34

    defaultConfig {
        applicationId = "mx.irisoft.pruebatecniva"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "URL_IMGS", "\"https://www.themoviedb.org/t/p/w220_and_h330_face\"")
            buildConfigField("String", "MDB_KEY", "\"cb310c0df71ce1cb6953dab65c5369e8\"")
            buildConfigField("Long", "TIMEOUT_SECONDS", "60L")
            buildConfigField("String", "DATE_FORMAT", "\"yyyy-MM-dd HH:mm:ss\"")
            buildConfigField("String", "DATE_FORMAT_INPUT", "\"yyyy-MM-dd\"")
            buildConfigField("String", "MONEY_FORMAT", "\"$###,####,###,###,###,##0.00\"")
            buildConfigField("String", "DECIMAL_FORMAT", "\"###,##0.00\"")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "URL_IMGS", "\"https://www.themoviedb.org/t/p/w220_and_h330_face\"")
            buildConfigField("String", "MDB_KEY", "\"cb310c0df71ce1cb6953dab65c5369e8\"")
            buildConfigField("Long", "TIMEOUT_SECONDS", "60L")
            buildConfigField("String", "DATE_FORMAT", "\"yyyy-MM-dd HH:mm:ss\"")
            buildConfigField("String", "DATE_FORMAT_INPUT", "\"yyyy-MM-dd\"")
            buildConfigField("String", "MONEY_FORMAT", "\"$###,####,###,###,###,##0.00\"")
            buildConfigField("String", "DECIMAL_FORMAT", "\"###,##0.00\"")
        }
    }
}

dependencies {

    //Core
    implementation(libs.androidxKtx)
    implementation(libs.multidex)
    implementation(libs.reflect)
    //Hilt
    implementation(libs.hilt)
    ksp(libs.hiltCompiler)
    //GUI
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.constraintLayout)
    implementation(libs.activityktx)
    implementation(libs.navFragment)
    implementation(libs.navUI)
    implementation(libs.lottie)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitConverter)
    implementation(libs.coroutineAdapter)
    implementation(libs.loggingInterceptor)
    //Glide
    implementation(libs.glide)
    ksp(libs.glideCompiler)
    //lifecycle
    implementation(libs.livedata)
    implementation(libs.viewModel)
    implementation(libs.work)
    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
}
