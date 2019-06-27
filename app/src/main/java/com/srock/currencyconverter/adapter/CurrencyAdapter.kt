package com.srock.currencyconverter.adapter

import android.icu.util.Currency
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.srock.currencyconverter.R
import com.srock.currencyconverter.adapter.CurrencyAdapter.CurrencyViewHolder
import com.srock.currencyconverter.data.CurrencyExchange
import com.srock.currencyconverter.data.CurrencyListed
import com.srock.currencyconverter.utils.CountryInformation
import com.srock.currencyconverter.utils.onChange
import kotlinx.android.synthetic.main.currency_exchange_view.view.*
import kotlinx.android.synthetic.main.currency_main_view.view.*
import java.text.DecimalFormat

class CurrencyAdapter(private val inputListener: InputListener) : RecyclerView.Adapter<CurrencyViewHolder>() {


    companion object {
        const val TYPE_MAIN = 0
        const val TYPE_EXCHANGE = 1
    }

    private var currencyValues : CurrencyListed? = null
    private val decimal = DecimalFormat("0.00")

    fun feedData(latestCurrency: CurrencyListed){

        currencyValues = latestCurrency
        notifyDataSetChanged()
//        currencyValues?.let {
//            val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(it,latestCurrency))
//            diffResult.dispatchUpdatesTo(this)
//        } ?: run {
//
//        }
    }

    interface BinderHolder{
        fun bind(exchange: CurrencyExchange, viewType: Int, clickListener: InputListener)
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view), BinderHolder {
        override fun bind(exchange: CurrencyExchange, viewType: Int, clickListener: InputListener) {
            when(viewType){
                TYPE_MAIN -> {
                    view.currency_main_short_name.text = exchange.key
                    view.currency_main_long_name.text = Currency.getInstance(exchange.key).displayName
                    view.currency_main_input_value.onChange { text -> clickListener.onInputChanged(text) }
                    view.flag_main_image_view.setImageResource(CountryInformation.getFlagId(exchange.key,view.context))
                }
                TYPE_EXCHANGE -> {
                    view.setOnClickListener { clickListener.onItemClicked(exchange.key) }
                    view.currency_exchange_short_name.text = exchange.key
                    view.currency_exchange_long_name.text = Currency.getInstance(exchange.key).displayName
                    view.currency_exchange_input_value.text = Editable.Factory.getInstance().newEditable(decimal.format(exchange.rate).toString())
                    view.flag_exchange_image_view.setImageResource(CountryInformation.getFlagId(exchange.key,view.context))
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
            return it.exchanges.size + 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        currencyValues?.let {
            if (position == 0) {
                holder.bind(CurrencyExchange(it.mainCurrency,it.multiplier),TYPE_MAIN,inputListener)
            } else {
                val exchange = it.exchanges[position-1]
                holder.bind(CurrencyExchange(exchange.key,it.multiplier*exchange.rate),TYPE_EXCHANGE,inputListener)
            }
        }

    }

//    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int, payloads: MutableList<Any>) {
//        if (payloads.isEmpty()) return
//        else {
//            val bundle = payloads[0] as Bundle
//            for(key in bundle.keySet()) {
//                if (key == CurrencyListed.RATE){
//
//                }
//            }
//        }
//    }
}