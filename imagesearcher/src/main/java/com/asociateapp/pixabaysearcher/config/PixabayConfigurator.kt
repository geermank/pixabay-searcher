package com.asociateapp.pixabaysearcher.config

import android.content.Intent

class PixabayConfigurator(intent: Intent) : Configurator(intent) {

    companion object {
        internal const val PIXABAY_SEARCH_TERM = "PIXABAY_SEARCH_TERM"
        internal const val PIXABAY_API_KEY = "PIXABAY_API_KEY"
        internal const val PIXABAY_API_IMAGES_PER_PAGE = "PIXABAY_API_IMAGES_PER_PAGE"

        private const val DEFAULT_IMAGES_PER_PAGE = 24
    }

    internal fun getApiKey() = intent.getStringExtra(PIXABAY_API_KEY).apply {
        check(!this.isNullOrEmpty()) { "You must provide a Pixabay API key in order to use this component" }
    }!!

    internal fun getImagesPerPage() = intent.getIntExtra(
        PIXABAY_API_IMAGES_PER_PAGE,
        DEFAULT_IMAGES_PER_PAGE
    )

    internal fun getSearchTerm() = intent.getStringExtra(PIXABAY_SEARCH_TERM) ?: ""
}