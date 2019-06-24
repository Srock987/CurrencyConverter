package com.srock.currencyconverter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.srock.currencyconverter.data.Currency

class CurrencyAdapter() : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var currency : Currency? = null

    fun feedData(latestCurrency: Currency){
        currency = latestCurrency
    }

    class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val currencyView = LayoutInflater.from(parent.context).inflate(R.layout.currency_view,parent,false)
        return CurrencyViewHolder(currencyView)
    }

    override fun getItemCount(): Int {
        currency?.let {
            return it.rates.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        currency?.let {
                val key = it.rates.keys.toList().get(position)
//                holder.view.findViewById<CircularImageView>(R.id.flag_image_view).setImageResource()
                holder.view.findViewById<TextView>(R.id.currency_short_name).text = key
                holder.view.findViewById<TextView>(R.id.currency_long_name).text = key
                holder.view.findViewById<EditText>(R.id.currency_input_value).text = Editable.Factory.getInstance().newEditable(it.rates[key].toString())
            }
    }




}