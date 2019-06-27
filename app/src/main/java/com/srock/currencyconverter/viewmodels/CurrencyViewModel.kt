package com.srock.currencyconverter.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.data.CurrencyListed
import com.srock.currencyconverter.data.Values
import com.srock.currencyconverter.networking.CurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyViewModel : BaseViewModel() {

    private lateinit var subscription: Disposable

    @Inject lateinit var repository: CurrencyRepository

    private var currency: Currency? = null


    private var inputAmount: Float = 100.0f

    private val currencyValue: MutableLiveData<CurrencyListed> by lazy {
        MutableLiveData<CurrencyListed>().also {
            loadCurrency(Values.STARTING_CURRENCY)
        }
    }

    fun getCurrency() : LiveData<CurrencyListed>{
        return currencyValue
    }

    fun inputChanged(inputString: String){
        val newInputAmount = inputString.toFloat()
        inputAmount = newInputAmount
        currency?.let {  currencyValue.value = CurrencyListed(newInputAmount,it) }

    }

    fun changeCurrency(currencyName: String){
        subscription.dispose()
        loadCurrency(currencyName)
    }

    private fun loadCurrency(currencyName: String) {
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
        currencyValue.value = CurrencyListed(inputAmount,newCurrency)
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