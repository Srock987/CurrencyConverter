package com.srock.currencyconverter

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CurrencyApplication : Application() {



    override fun onCreate() {
        super.onCreate()
//        DaggerApplicationComponent
//            .builder()
//            .application(this)
//            .build()
//            .inject(this)
    }
}