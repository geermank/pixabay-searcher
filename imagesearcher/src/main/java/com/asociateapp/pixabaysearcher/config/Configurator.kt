package com.asociateapp.pixabaysearcher.config

import android.content.Intent

open class Configurator(protected val intent: Intent) {

    companion object {
        const val IMAGE_SEARCHER_SELECTED_IMAGE_URI = "IMAGE_SEARCHER_SELECTED_IMAGE_URI"
        const val IMAGE_SEARCHER_RC = 1111

        internal const val ACTIVITY_TITLE = "IMAGE_SEARCHER_ACTIVITY_TITLE"
        internal const val ACTIVITY_SHOW_UP_BUTTON = "IMAGE_SEARCHER_ACTIVITY_SHOW_UP_BUTTON"
        internal const val ACTIVITY_DELIVER_URI_RESULT = "IMAGE_SEARCHER_ACTIVITY_DELIVER_URI_RESULT"
    }

    internal fun getActivityTitle() = intent.getStringExtra(ACTIVITY_TITLE) ?: ""

    internal fun showUpButton() = intent.getBooleanExtra(ACTIVITY_SHOW_UP_BUTTON, false)

    internal fun expectingUriResult() = intent.getBooleanExtra(ACTIVITY_DELIVER_URI_RESULT, true)

}