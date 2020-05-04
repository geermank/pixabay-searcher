package com.asociateapp.pixabaysearcher.utils

import android.view.View

fun View.changeVisibility(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}