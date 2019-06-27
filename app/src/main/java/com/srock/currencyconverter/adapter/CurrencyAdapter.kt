package com.srock.currencyconverter.adapter

import android.icu.util.Currency
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.srock.currencyconverter.R
import com.srock.currencyconverter.adapter.CurrencyAdapter.CurrencyViewHolder
import com.srock.currencyconverter.data.CurrencyExchange
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

    private var currencyExchangesList : List<CurrencyExchange> = emptyList<CurrencyExchange>()
    private val decimal = DecimalFormat("0.00")

    fun feedData(currencyExchangeListUpdate: List<CurrencyExchange>){
        val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(currencyExchangesList,currencyExchangeListUpdate))
        currencyExchangesList = currencyExchangeListUpdate
        diffResult.dispatchUpdatesTo(this)
    }

    fun gainMainFocus() {

    }



    interface BinderHolder{
        fun bind(exchange: CurrencyExchange, viewType: Int, clickListener: InputListener)
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view), BinderHolder {

        override fun bind(exchange: CurrencyExchange, viewType: Int, clickListener: InputListener) {
            when(viewType){
                TYPE_MAIN -> {
                    view.currencyMainShortName.text = exchange.key
                    view.currencyMainLongName.text = Currency.getInstance(exchange.key).displayName
                    view.currencyMainInputValue.onChange { text -> clickListener.onInputChanged(text) }
                    view.flagMainImageView.setImageResource(CountryInformation.getFlagId(exchange.key,view.context))
                }
                TYPE_EXCHANGE -> {
                    view.setOnClickListener { clickListener.onItemClicked(exchange.key) }
                    view.currencyExchangeShortName.text = exchange.key
                    view.currencyExchangeLongName.text = Currency.getInstance(exchange.key).displayName
                    view.currencyExchangeInputValue.text = Editable.Factory.getInstance().newEditable(decimal.format(exchange.rate).toString())
                    view.flagExchangeImageView.setImageResource(CountryInformation.getFlagId(exchange.key,view.context))
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
        return positionType(position)
    }

    fun positionType(position: Int) : Int {
        return if (position == 0) TYPE_MAIN else TYPE_EXCHANGE
    }

    override fun getItemCount(): Int {
        return currencyExchangesList.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        if (position == 0) {
            holder.bind(currencyExchangesList[position],TYPE_MAIN,inputListener)
        } else {
            holder.bind(currencyExchangesList[position],TYPE_EXCHANGE,inputListener)
        }
    }

}