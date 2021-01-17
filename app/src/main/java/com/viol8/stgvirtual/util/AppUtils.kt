package com.viol8.stgvirtual.util

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.viol8.stgvirtual.R

fun Activity.snackbar(message: String) {
    val snackbar =
        Snackbar.make(this.findViewById<View>(android.R.id.content), message, 6 * 1000)

    // Changing action button text color
    val sbView = snackbar.view
    sbView.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    textView.maxLines = 5

    snackbar.setAction("Ok") {
        snackbar.dismiss()
    }
    snackbar.show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}