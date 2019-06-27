package com.srock.currencyconverter.adapter

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.srock.currencyconverter.data.CurrencyListed

class CurrencyDiffCallback(private val newCurrencyListed: CurrencyListed, private val oldCurrencyListed: CurrencyListed ) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d("Diff-old",oldItemPosition.toString())
        Log.d("Diff-new",newItemPosition.toString())
        return oldCurrencyListed.exchanges[oldItemPosition].key == newCurrencyListed.exchanges[newItemPosition].key
//                ||
//                oldCurrencyListed.exchanges[oldItemPosition].key == newCurrencyListed.mainCurrency ||
//                oldCurrencyListed.mainCurrency == newCurrencyListed.exchanges[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return oldCurrencyListed.exchanges.size + 1
    }

    override fun getNewListSize(): Int {
        return newCurrencyListed.exchanges.size + 1
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencyListed.exchanges[oldItemPosition].rate == newCurrencyListed.exchanges[newItemPosition].rate
//                ||
//                oldCurrencyListed.exchanges[oldItemPosition].rate == newCurrencyListed.multiplier ||
//                oldCurrencyListed.multiplier == newCurrencyListed.exchanges[newItemPosition].rate
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val diffBundle = Bundle()
        if (newCurrencyListed.exchanges[newItemPosition].rate.equals(oldCurrencyListed.exchanges[oldItemPosition].rate)){
            diffBundle.putFloat(CurrencyListed.RATE,newCurrencyListed.exchanges[newItemPosition].rate)
        }
        if (diffBundle.size() == 0) return null
        return diffBundle
    }
}