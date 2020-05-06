package com.asociateapp.pixabaysearcher.config

import android.content.Context
import android.content.Intent
import com.asociateapp.pixabaysearcher.presentation.GalleryActivity

open class ConfigBuilder(context: Context) {

    protected val intent = Intent(context, GalleryActivity::class.java)

    fun setActivityTitle(value: String) {
        this.intent.putExtra(Configurator.ACTIVITY_TITLE, value)
    }

    fun showActivityUpButton(value: Boolean) {
        this.intent.putExtra(Configurator.ACTIVITY_SHOW_UP_BUTTON, value)
    }

    fun selectedImageUriIsExpected(value: Boolean) {
        this.intent.putExtra(Configurator.ACTIVITY_DELIVER_URI_RESULT, value)
    }

    fun createIntent() = intent

}