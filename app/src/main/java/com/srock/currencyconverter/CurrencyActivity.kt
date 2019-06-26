package com.srock.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.srock.currencyconverter.adapter.CurrencyAdapter
import com.srock.currencyconverter.adapter.InputListener
import com.srock.currencyconverter.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : AppCompatActivity(), InputListener {




    lateinit var model: CurrencyViewModel
    lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        model = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        adapter = CurrencyAdapter(this)
        currency_recycler_view.adapter = adapter
        currency_recycler_view.layoutManager = LinearLayoutManager(this)
        currency_recycler_view.setOnTouchListener { view, _ ->
            view?.performClick()
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
            false
        }
        model.getCurrency().observe(this, Observer { currency ->
            adapter.feedData(currency)
        })
        model.loadCurrency("EUR")
    }

    override fun onResume() {
        super.onResume()
        model.updateContinuously()
    }

    override fun onPause() {
        super.onPause()
        model.stopContinuousUpdates()
    }



    override fun onInputChanged(inputString: String) {
        model.inputChanged(inputString)
    }

    override fun onItemClicked(key: String) {
        model.loadCurrency(key)
        currency_recycler_view.smoothScrollToPosition(0)
    }
}
