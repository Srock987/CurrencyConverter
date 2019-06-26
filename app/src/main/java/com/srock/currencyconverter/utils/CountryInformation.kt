package com.srock.currencyconverter.utils

import android.content.Context
import android.util.Log
import com.srock.currencyconverter.R

class CountryInformation {
    companion object {
        var countyMap: Map<Int, String>? = null

        fun getCountryShortName(countryNumber: Int, context: Context) : String {
            if (countyMap==null){
                countyMap = countriesMap(context)
            }
            countyMap?.let {
                return it.getOrElse(countryNumber){"PL"}
            }
            return "PL"
        }

        private fun countriesMap(context: Context) : Map<Int,String>{
            val map = mutableMapOf<Int,String>()
            context.resources.getStringArray(R.array.currency_to_country_codes).forEach { codeName ->
                val slitted = codeName.split(",")
                map[slitted.first().toInt()] = slitted.last()
            }
            return map
        }

        fun getFlagId(countryLongKey: String, context: Context) : Int{
            return context.resources.getIdentifier(countryLongKey.substring(0,2).toLowerCase(),"drawable",context.packageName)
        }
    }
}