package ru.eupavlov.btms

import android.util.Log
import android.view.View
import java.lang.Exception

fun View.showOrHide(flag: Boolean) {
    if (flag) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun safe(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        Log.e(e.stackTrace.firstOrNull()?.className?:"NO_CLASS_NAME", e.toString())
        e.printStackTrace()
    }
}