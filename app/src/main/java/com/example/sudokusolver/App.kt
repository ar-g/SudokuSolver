package com.example.sudokusolver

import android.app.Application
import com.example.sudokusolver.di.AppComponent
import com.example.sudokusolver.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}