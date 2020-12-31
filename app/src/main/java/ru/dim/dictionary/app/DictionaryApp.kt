package ru.dim.dictionary.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.dim.dictionary.di.*

class DictionaryApp : Application() {

    companion object{
        private lateinit var instance: DictionaryApp
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin{
            androidLogger()
//            androidContext(this@DictionaryApp)
            androidContext(applicationContext)
//            modules(listOf(viewModelModule, interactorModule, repositoryModule))
        }
    }
}