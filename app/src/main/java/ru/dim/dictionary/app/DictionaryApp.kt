package ru.dim.dictionary.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.dim.dictionary.di.*

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@DictionaryApp)
            modules(listOf(viewModelModule, interactorModule, repositoryModule))
        }
    }
}