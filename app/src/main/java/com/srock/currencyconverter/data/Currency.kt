package com.srock.currencyconverter.data

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

data class Currency(@SerializedName("base") val base: String, @SerializedName("date") val date: Date,@SerializedName("rates") val rates: LinkedHashMap<String,Float>){

}