package com.asociateapp.pixabaysearcher.utils.extensions

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap


fun View.changeVisibility(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun ImageView.asBitmap(): Bitmap {
    return drawable.toBitmap()
}