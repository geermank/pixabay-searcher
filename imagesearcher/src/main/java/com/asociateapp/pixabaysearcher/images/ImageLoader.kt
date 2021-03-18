package com.asociateapp.pixabaysearcher.images

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.squareup.picasso.Transformation
import java.lang.Exception

internal object ImageLoader {

    fun load(url: String, target: ImageView, onLoad: () -> Unit = {}, onError: () -> Unit = {}) {
        Picasso.get().load(url).into(target, object : Callback {
            override fun onSuccess() {
                onLoad()
            }
            override fun onError(e: Exception?) {
                onError()
            }
        })
    }

    fun loadAsBitmap(url: String, target: ImageView, onLoad: () -> Unit = {}, onError: () -> Unit = {}) {
        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let { target.setImageBitmap(it) }
                onLoad()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                onError()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // do nothing
            }
        })
    }
}