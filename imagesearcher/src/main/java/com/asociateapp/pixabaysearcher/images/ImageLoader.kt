package com.asociateapp.pixabaysearcher.images

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
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
}