package ru.dim.dictionary.app

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.dim.dictionary.di.AppComponent
import ru.dim.dictionary.di.DaggerAppComponent
import javax.inject.Inject

class DictionaryApp : Application(), HasActivityInjector {
//    companion object {
//        lateinit var component: AppComponent
//    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
//        component =
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector
}