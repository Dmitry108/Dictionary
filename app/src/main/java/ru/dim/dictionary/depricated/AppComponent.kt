package ru.dim.dictionary.depricated

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ru.dim.dictionary.app.DictionaryApp
import javax.inject.Singleton

@Component(modules = [
    ViewModelModule::class,
    InteractorModule::class,
    RepositoryModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface ComponentBuilder {
        @BindsInstance
        fun application(application: Application): ComponentBuilder
        fun build(): AppComponent
    }

//    fun inject(activity: MainActivity)
    fun inject(app: DictionaryApp)
}