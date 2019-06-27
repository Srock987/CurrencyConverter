package com.srock.currencyconverter.data

data class CurrencyListed(val mainCurrency: String,val multiplier: Float,val exchanges: List<CurrencyExchange>){
    companion object {
        const val RATE = "RATE"
    }
    constructor(inputMultiplier: Float, currency: Currency):this(currency.base,inputMultiplier,currency.rates.map { entry -> CurrencyExchange(entry.key,entry.value) })
}