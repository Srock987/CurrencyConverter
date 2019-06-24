package com.srock.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.srock.currencyconverter.models.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : AppCompatActivity() {


    lateinit var model: CurrencyViewModel
    lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        model = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        adapter = CurrencyAdapter()
        currency_recycler_view.adapter = adapter
        currency_recycler_view.layoutManager = LinearLayoutManager(this)
        model.getCurrency().observe(this, Observer { currency ->
            adapter.feedData(currency)
        })
        model.loadCurrency("EUR")
    }
}
