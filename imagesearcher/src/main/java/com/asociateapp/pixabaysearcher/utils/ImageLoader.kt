package com.asociateapp.pixabaysearcher.utils

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

object ImageLoader {

    fun load(url: String, target: ImageView, onLoad: () -> Unit = {}, onError: () -> Unit = {}) {
        Picasso.get().load(url).into(target, object : Callback {
            override fun onSuccess() {
                onLoad()
            }
            override fun onError(e: java.lang.Exception?) {
                onError()
            }
        })
    }

}