package ru.dim.dictionary.di

import dagger.Component
import ru.dim.dictionary.view.MainActivity
import javax.inject.Singleton

@Component(modules = [
    InteractorModule::class,
    RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}