package com.srock.currencyconverter.utils

import android.content.Context
import android.util.Log
import com.srock.currencyconverter.R

class CountryInformation {
    companion object {

        fun getFlagId(countryLongKey: String, context: Context) : Int{
            return context.resources.getIdentifier(countryLongKey.substring(0,2).toLowerCase(),"drawable",context.packageName)
        }
    }
}