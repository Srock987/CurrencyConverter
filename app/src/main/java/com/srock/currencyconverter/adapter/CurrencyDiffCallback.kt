package com.srock.currencyconverter.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.srock.currencyconverter.data.CurrencyExchange

class CurrencyDiffCallback(private val newCurrencyList: List<CurrencyExchange>, private val oldCurrencyList: List<CurrencyExchange> ) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencyList[oldItemPosition].key == newCurrencyList[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return oldCurrencyList.size
    }

    override fun getNewListSize(): Int {
        return newCurrencyList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencyList[oldItemPosition] == newCurrencyList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val diffBundle = Bundle()
        if (!newCurrencyList[newItemPosition].rate.equals(oldCurrencyList[oldItemPosition].rate)){
            diffBundle.putFloat(CurrencyExchange.RATE,newCurrencyList[newItemPosition].rate)
        }
//        if (!newCurrencyList[newItemPosition].key.equals(oldCurrencyList[oldItemPosition].key)){
//            diffBundle.putString(CurrencyExchange.KEY,newCurrencyList[newItemPosition].key)
//        }
        if (diffBundle.size() == 0) return null
        return diffBundle
    }
}