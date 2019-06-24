package com.srock.currencyconverter.data

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

data class Currency(val name: String,val date: Date, val rates: LinkedHashMap<String,Float>)