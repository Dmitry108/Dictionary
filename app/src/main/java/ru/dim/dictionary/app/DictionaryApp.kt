package ru.dim.dictionary.app

import android.app.Application
import ru.dim.dictionary.di.AppComponent
import ru.dim.dictionary.di.DaggerAppComponent

class DictionaryApp : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}