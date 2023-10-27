package com.example.betting.wrappers

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Context.hideKeyboard(editText: EditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}