package com.srock.currencyconverter.models

import androidx.lifecycle.ViewModel
import com.srock.currencyconverter.networking.NetworkModule

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector.builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is CurrencyViewModel -> injector.inject(this)
        }
    }
}