package com.srock.currencyconverter.networking

import com.srock.currencyconverter.data.Currency
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyRepositoryImpl(var service : CurrencyService) : CurrencyRepository {

    override fun getCurrenciesUpdates(currencyName: String) : Observable<Currency> {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io()).flatMap{ service.getCurrency(currencyName) }
    }

}