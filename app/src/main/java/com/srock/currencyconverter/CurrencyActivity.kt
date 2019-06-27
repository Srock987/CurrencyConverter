package com.srock.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        model = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        adapter = CurrencyAdapter(this)
        currencyRecyclerView.adapter = adapter
        currencyRecyclerView.layoutManager = layoutManager
        currencyRecyclerView.setOnTouchListener { view, _ ->
            view?.performClick()
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
            false
        }
        model.getViewState().observe(this, Observer { viewState ->
            adapter.feedData(viewState.currencyExchanges)
            if (viewState.shouldScroll) {
                currencyRecyclerView.smoothScrollToPosition(0)
                val view = layoutManager.findViewByPosition(0)
                val edit = view?.findViewById<EditText>(R.id.currencyMainInputValue)
                edit?.requestFocus()
            }
        })
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
        model.changeCurrency(key)
    }
}
