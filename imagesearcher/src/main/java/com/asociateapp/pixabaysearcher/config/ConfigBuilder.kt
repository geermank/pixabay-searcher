package com.asociateapp.pixabaysearcher.config

import android.content.Context
import android.content.Intent
import com.asociateapp.pixabaysearcher.presentation.GalleryActivity

class ConfigBuilder(
    context: Context,
    pixabayApiKey: String
) {

    private val intent = Intent(context, GalleryActivity::class.java)

    init {
        this.intent.putExtra(Configurator.PIXABAY_API_KEY, pixabayApiKey)
    }

    fun setActivityTitle(value: String): ConfigBuilder {
        this.intent.putExtra(Configurator.ACTIVITY_TITLE, value)
        return this
    }

    fun showActivityUpButton(value: Boolean): ConfigBuilder {
        this.intent.putExtra(Configurator.ACTIVITY_SHOW_UP_BUTTON, value)
        return this
    }

    fun selectedImageUriIsExpected(value: Boolean): ConfigBuilder {
        this.intent.putExtra(Configurator.ACTIVITY_DELIVER_URI_RESULT, value)
        return this
    }

    fun setSearchTerm(value: String): ConfigBuilder {
        this.intent.putExtra(Configurator.PIXABAY_SEARCH_TERM, value)
        return this
    }

    fun setImagesPerPageExpected(value: Int): ConfigBuilder {
        this.intent.putExtra(Configurator.PIXABAY_API_IMAGES_PER_PAGE, value)
        return this
    }

    fun build() = intent

}