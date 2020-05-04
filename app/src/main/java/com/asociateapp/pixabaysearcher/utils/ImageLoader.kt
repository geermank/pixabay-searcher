package com.asociateapp.pixabaysearcher.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoader {

    fun load(url: String, target: ImageView) {
        Picasso.get().load(url).into(target)
    }

}