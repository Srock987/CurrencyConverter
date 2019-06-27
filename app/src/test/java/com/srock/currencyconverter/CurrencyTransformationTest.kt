package com.srock.currencyconverter

import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.data.CurrencyExchange
import org.junit.Test
import java.util.*

class CurrencyTransformationTest {

    @Test
    fun testCurrencyTransformation(){
        val base = "EUR"
        val date = Date()
        val middlePair = ("GBP" to 2.0f)
        val lastPair = ("PLN" to 0.5f)
        val ratesMap = linkedMapOf<String,Float>(middlePair,lastPair)
        val currency = Currency(base,date,ratesMap)
        val multiplier = 4.0f

        val listedCurrency = CurrencyExchange.Factory.transformCurrency(currency)

        for ((index,exchange) in listedCurrency.withIndex()){
            when (index){
                0 -> {
                    assert(exchange.key==base)
                    assert(exchange.rate.equals(1.0f))
                }
                1 -> {
                    assert(exchange.key==middlePair.first)
                    assert(exchange.rate.equals(middlePair.second))
                }
                2 -> {
                    assert(exchange.key==lastPair.first)
                    assert(exchange.rate.equals(lastPair.second))
                }
            }
        }

    }
}