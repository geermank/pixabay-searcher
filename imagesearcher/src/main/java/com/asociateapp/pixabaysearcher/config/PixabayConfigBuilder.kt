package com.asociateapp.pixabaysearcher.config

import android.content.Context

class PixabayConfigBuilder(
    context: Context,
    pixabayApiKey: String
) : ConfigBuilder(context) {

    init {
        this.intent.putExtra(PixabayConfigurator.PIXABAY_API_KEY, pixabayApiKey)
    }

    fun setSearchTerm(value: String) {
        this.intent.putExtra(PixabayConfigurator.PIXABAY_SEARCH_TERM, value)
    }

    fun setImagesPerPageExpected(value: Int) {
        this.intent.putExtra(PixabayConfigurator.PIXABAY_API_IMAGES_PER_PAGE, value)
    }

}