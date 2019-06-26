package com.srock.currencyconverter.adapter

interface InputListener {
    fun onInputChanged(inputString: String)
    fun onItemClicked(key: String)
}