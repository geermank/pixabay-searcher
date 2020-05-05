package com.asociateapp.pixabaysearcher.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView

fun View.changeVisibility(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun ImageView.asBitmap(): Bitmap = (drawable as BitmapDrawable).bitmap