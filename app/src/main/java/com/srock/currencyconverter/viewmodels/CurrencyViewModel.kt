package com.srock.currencyconverter.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.data.CurrencyValues
import com.srock.currencyconverter.networking.CurrencyRepository
import com.srock.currencyconverter.networking.CurrencyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import javax.inject.Inject

class CurrencyViewModel : BaseViewModel() {

    private lateinit var subscription: Disposable

//    @Inject lateinit var currencyService: CurrencyService

    @Inject lateinit var repository: CurrencyRepository

    private var currency: Currency? = null


    private var inputAmount: Float = 100.0f

    private val currencyValue: MutableLiveData<CurrencyValues> = MutableLiveData()

    fun getCurrency() : LiveData<CurrencyValues>{
        return currencyValue
    }

    fun inputChanged(inputString: String){
        val newInputAmount = inputString.toFloat()
        inputAmount = newInputAmount
        currency?.let {  currencyValue.value = CurrencyValues(it,newInputAmount) }

    }

    fun loadCurrency(currencyName: String) {
        subscription = repository.getCurrenciesUpdates(currencyName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCurrencySuccess(it)},
                { onRetrieveCurrencyError() }
            )
    }

    private fun onRetrieveCurrencySuccess(newCurrency: Currency){
        currency = newCurrency
        currencyValue.value = CurrencyValues(newCurrency,inputAmount)
    }

    private fun onRetrieveCurrencyError(){
        Log.d("CurrencyViewModel","Error on receiving currency")
    }

    fun updateContinuously(){

    }

    fun stopContinuousUpdates(){

    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}