package ru.dim.dictionary.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.dim.dictionary.view.MainActivity
import javax.inject.Singleton

@Component(modules = [
    ViewModelModule::class,
    InteractorModule::class,
    RepositoryModule::class]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface ComponentBuilder {
        @BindsInstance
        fun application(application: Application): ComponentBuilder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}