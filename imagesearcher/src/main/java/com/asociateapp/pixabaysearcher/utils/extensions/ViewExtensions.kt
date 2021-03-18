package com.asociateapp.pixabaysearcher.utils.extensions

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
    return if (drawable is BitmapDrawable) {
        (drawable as BitmapDrawable).bitmap
    } else {
        drawable.toBitmap()
    }
}