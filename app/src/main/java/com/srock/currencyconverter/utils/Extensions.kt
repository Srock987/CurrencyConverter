package com.srock.currencyconverter.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onChange(textChanged: (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            textChanged(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    })
}