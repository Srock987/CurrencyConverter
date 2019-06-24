package com.srock.currencyconverter.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.networking.CurrencyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyViewModel : BaseViewModel() {

    private lateinit var subscription: Disposable

    @Inject lateinit var currencyService: CurrencyService

    private val currency: MutableLiveData<Currency> by lazy {
        MutableLiveData<Currency>().also {
//            loadCurrency("EUR")
        }
    }

    fun getCurrency() : LiveData<Currency>{
        return currency
    }

    fun loadCurrency(currencyName: String) {
        subscription = currencyService.getCurrency(currencyName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCurrencySuccess(it)},
                { onRetrieveCurrencyError() }
            )
    }

    private fun onRetrieveCurrencySuccess(newCurrency: Currency){
        currency.value = newCurrency
        Log.d("CurrencyViewModel","Success on receiving currency")

    }

    private fun onRetrieveCurrencyError(){
        Log.d("CurrencyViewModel","Error on receiving currency")
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}