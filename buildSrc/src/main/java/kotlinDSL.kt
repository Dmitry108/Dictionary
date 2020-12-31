import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "ru.dim.dictionary"
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    val javaVersion = JavaVersion.VERSION_1_8
    const val kotlinVersion = "1.8"
}

object Releases {
    const val versionName = "1.0"
    val versionCode: Int = getCode()

    private fun getCode(): Int{
        val pair = versionName.split(".")
        return pair[0].toInt()*1000 + pair[1].toInt()
    }
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val repository = ":repository"
    const val model = ":model"
    const val utils = ":utils"
    const val historyscreen = ":historyscreen"
}

object Versions {
    //Kotlin
    const val core = "1.3.2"
    const val stdLib = "1.4.10"
    //Design
    const val appcompat = "1.2.0"
    const val material = "1.2.1"
    //Layout
    const val constraintlayout = "2.0.3"
    const val swiperefreshlayout = "1.1.0"
    // Rx-Java
    const val rxandroid = "2.1.1"
    const val rxjava = "2.2.9"
    // Retrofit 2
    const val retrofit = "2.6.0"
    const val converterGson = "2.6.0"
    const val interceptor = "3.12.1"
    const val rxjava2Adapter = "1.0.0"
    const val coroutinesAdapter = "0.9.2"
    // Test
    const val junit = "4.13"
    const val runner = "1.3.0"
    const val espressoCore = "3.3.0"
    const val extJunit = "1.1.2"
    const val coreTesting = "1.1.1"
    // Mockk
    const val mockk = "1.10.0"
    // Picasso
    const val picasso = "2.5.2"
    // Dagger
    const val dagger = "2.17"
    // Koin
    const val koin = "2.0.1"
    // Coroutines
    const val coroutines = "1.3.9"
    // Lifecycle
    const val lifecycle = "2.2.0"
    // Room
    const val room = "2.3.0-alpha03"
    // Google
    const val googlePlayCore = "1.9.0"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val swiperefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.stdLib}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object RxJava {
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val rxjavaAdapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.rxjava2Adapter}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
}

object Testing {
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val coreTesting = "android.arch.core:core-testing:${Versions.coreTesting}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Dagger {
    const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val androidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
}

object Koin {
    const val android = "org.koin:koin-android:${Versions.koin}"
    const val androidViewmodel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Lifecycle {
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Google {
    const val playCore = "com.google.android.play:core:${Versions.googlePlayCore}"
}