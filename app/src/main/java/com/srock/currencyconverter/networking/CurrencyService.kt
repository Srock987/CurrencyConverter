package com.srock.currencyconverter.networking

import com.srock.currencyconverter.data.Currency
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("/latest")
    fun getCurrency(@Query("base") base : String) : Observable<Currency>

}