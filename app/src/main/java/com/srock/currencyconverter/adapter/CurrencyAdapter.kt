package com.srock.currencyconverter.adapter

import android.icu.util.Currency
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.srock.currencyconverter.R
import com.srock.currencyconverter.adapter.CurrencyAdapter.CurrencyViewHolder
import com.srock.currencyconverter.data.CurrencyInfo
import com.srock.currencyconverter.data.CurrencyValues
import com.srock.currencyconverter.utils.CountryInformation
import com.srock.currencyconverter.utils.onChange
import kotlinx.android.synthetic.main.currency_exchange_view.view.*
import kotlinx.android.synthetic.main.currency_main_view.view.*

class CurrencyAdapter(private val inputListener: InputListener) : RecyclerView.Adapter<CurrencyViewHolder>() {


    companion object {
        const val TYPE_MAIN = 0
        const val TYPE_EXCHANGE = 1
    }

    private var currencyValues : CurrencyValues? = null

    fun feedData(latestCurrency: CurrencyValues){
        currencyValues = latestCurrency
        notifyDataSetChanged()
    }

    interface BinderHolder{
        fun bind(info: CurrencyInfo, viewType: Int, clickListener: InputListener)
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view), BinderHolder {
        override fun bind(info: CurrencyInfo, viewType: Int, clickListener: InputListener) {
            when(viewType){
                TYPE_MAIN -> {
                    view.currency_main_short_name.text = info.key
                    view.currency_main_long_name.text = Currency.getInstance(info.key).displayName
                    view.currency_main_input_value.onChange { text -> clickListener.onInputChanged(text) }
                    view.flag_main_image_view.setImageResource(CountryInformation.getFlagId(info.key,view.context))
                }
                TYPE_EXCHANGE -> {
                    view.setOnClickListener { clickListener.onItemClicked(info.key) }
                    view.currency_exchange_short_name.text = info.key
                    view.currency_exchange_long_name.text = Currency.getInstance(info.key).displayName
                    view.currency_exchange_input_value.text = Editable.Factory.getInstance().newEditable(info.rate.toString())
                    view.flag_exchange_image_view.setImageResource(CountryInformation.getFlagId(info.key,view.context))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val currencyView = when(viewType){
            TYPE_MAIN -> LayoutInflater.from(parent.context).inflate(R.layout.currency_main_view,parent,false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.currency_exchange_view,parent,false)
        }
        return CurrencyViewHolder(currencyView)
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (position) {
            0 -> TYPE_MAIN
            else -> TYPE_EXCHANGE
        }
        return type
    }

    override fun getItemCount(): Int {
        currencyValues?.let {
            return it.currency.rates.size + 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        currencyValues?.let {
            if (position == 0) {
                holder.bind(CurrencyInfo(it.currency.base,it.multiplier),TYPE_MAIN,inputListener)
            } else {
                val key = it.currency.rates.keys.toList()[position-1]
                it.currency.rates[key]?.let { rate ->
                    holder.bind(CurrencyInfo(key,it.multiplier*rate),TYPE_EXCHANGE,inputListener)
                }
            }
        }

    }

}