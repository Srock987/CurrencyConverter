package com.srock.currencyconverter.data

data class CurrencyExchange(val key: String, val rate: Float){

    companion object {
        const val RATE = "RATE"
        const val KEY = "KEY"
    }

    class Factory {
        companion object {
            fun transformCurrency(currency: Currency) : List<CurrencyExchange> {
                val list= mutableListOf<CurrencyExchange>(CurrencyExchange(currency.base,1.0f))
                list.addAll(currency.rates.map { entry -> CurrencyExchange(entry.key, entry.value) })
                return list
            }
        }
    }
}