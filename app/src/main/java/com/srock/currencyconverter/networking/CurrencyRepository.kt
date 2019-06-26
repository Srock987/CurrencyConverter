package com.srock.currencyconverter.networking

import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.networking.CurrencyService
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRepository(var service : CurrencyService) {

    fun getCurrenciesUpdates(currencyName: String) : Observable<Currency>{
       return service.getCurrency(currencyName)
    }

}