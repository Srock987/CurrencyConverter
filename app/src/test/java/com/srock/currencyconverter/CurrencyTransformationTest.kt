package com.srock.currencyconverter

import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.data.CurrencyListed
import org.junit.Test
import java.util.*
import kotlin.collections.LinkedHashMap

class CurrencyTransformationTest {

    @Test
    fun testCurrencyTransformation(){
        val base = "EUR"
        val date = Date()
        val firstPair = ("GBP" to 2.0f)
        val lastPair = ("PLN" to 0.5f)
        val ratesMap = linkedMapOf<String,Float>(firstPair,lastPair)
        val currency = Currency(base,date,ratesMap)
        val multiplier = 4.0f

        val listedCurrency = CurrencyListed(multiplier,currency)

        assert(listedCurrency.mainCurrency == currency.base)
        assert(listedCurrency.multiplier == multiplier)
        assert(listedCurrency.exchanges.size == 2)
        assert(listedCurrency.exchanges.first().key == firstPair.first)
        assert((listedCurrency.exchanges.first().rate * multiplier).equals(multiplier * firstPair.second))
        assert(listedCurrency.exchanges.last().key == lastPair.first)
        assert((listedCurrency.exchanges.last().rate * multiplier).equals(multiplier * lastPair.second))
    }
}