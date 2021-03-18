package com.asociateapp.pixabaysearcher.utils.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}