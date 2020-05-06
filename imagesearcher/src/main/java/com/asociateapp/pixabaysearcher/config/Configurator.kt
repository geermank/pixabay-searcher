package com.asociateapp.pixabaysearcher.config

import android.content.Intent

class Configurator(private val intent: Intent) {

    companion object {
        const val IMAGE_SEARCHER_SELECTED_IMAGE_URI = "IMAGE_SEARCHER_SELECTED_IMAGE_URI"
        const val IMAGE_SEARCHER_RC = 1111

        internal const val ACTIVITY_TITLE = "PIXABAY_SEARCHER_ACTIVITY_TITLE"
        internal const val ACTIVITY_SHOW_UP_BUTTON = "PIXABAY_SEARCHER_SHOW_UP_BUTTON"
        internal const val ACTIVITY_DELIVER_URI_RESULT = "ACTIVITY_DELIVER_URI_RESULT"

        internal const val PIXABAY_SEARCH_TERM = "PIXABAY_SEARCH_TERM"
        internal const val PIXABAY_API_KEY = "PIXABAY_API_KEY"
        internal const val PIXABAY_API_IMAGES_PER_PAGE = "PIXABAY_API_IMAGES_PER_PAGE"

        private const val DEFAULT_IMAGES_PER_PAGE = 24
    }

    internal fun getActivityTitle() = intent.getStringExtra(ACTIVITY_TITLE) ?: ""

    internal fun showUpButton() = intent.getBooleanExtra(ACTIVITY_SHOW_UP_BUTTON, false)

    internal fun expectingUriResult() = intent.getBooleanExtra(ACTIVITY_DELIVER_URI_RESULT, true)

    internal fun getApiKey() = intent.getStringExtra(PIXABAY_API_KEY).apply {
        check(!this.isNullOrEmpty()) { "You must provide a Pixabay API key in order to use this component" }
    }!!

    internal fun getImagesPerPage() = intent.getIntExtra(
        PIXABAY_API_IMAGES_PER_PAGE,
        DEFAULT_IMAGES_PER_PAGE
    )

    internal fun getSearchTerm() = intent.getStringExtra(PIXABAY_SEARCH_TERM) ?: ""
}