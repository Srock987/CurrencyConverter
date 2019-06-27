package com.srock.currencyconverter.networking

import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.networking.CurrencyService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface CurrencyRepository{
    fun getCurrenciesUpdates(currencyName: String) : Observable<Currency>
}
