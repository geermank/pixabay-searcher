package com.asociateapp.pixabaysearcher

import android.content.Intent

class Configurator(private val intent: Intent) {

    companion object {
        const val ACTIVITY_TITLE = "PIXABAY_SEARCHER_ACTIVITY_TITLE"
        const val ACTIVITY_SHOW_UP_BUTTON = "PIXABAY_SEARCHER_SHOW_UP_BUTTON"
        const val PIXABAY_API_KEY = "PIXABAY_API_KEY"
        const val PIXABAY_API_IMAGES_PER_PAGE = "PIXABAY_API_IMAGES_PER_PAGE"

        private const val DEFAULT_IMAGES_PER_PAGE = 24
    }

    fun getActivityTitle() = intent.getStringExtra(ACTIVITY_TITLE) ?: ""

    fun showUpButton() = intent.getBooleanExtra(ACTIVITY_SHOW_UP_BUTTON, false)

    fun getApiKey() = intent.getStringExtra(PIXABAY_API_KEY).apply {
        check(!this.isNullOrEmpty()) { "You must provide a Pixabay API key in order to use this component" }
    }!!

    fun getImagesPerPage() = intent.getIntExtra(PIXABAY_API_IMAGES_PER_PAGE, DEFAULT_IMAGES_PER_PAGE)
}