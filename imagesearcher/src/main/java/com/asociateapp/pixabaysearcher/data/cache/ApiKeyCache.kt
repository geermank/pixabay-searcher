package com.asociateapp.pixabaysearcher.data.cache

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val API_CONFIG_FILE = "API_CONFIG_FILE"
private const val API_KEY = "API_KEY"

internal class ApiKeyCache @Inject constructor(@ApplicationContext context: Context) {

    private val preferences = context.getSharedPreferences(API_CONFIG_FILE, Context.MODE_PRIVATE)

    fun save(key: String) {
        if (checkValueAlreadySaved(key)) {
            return
        }
        preferences.edit()
            .putString(API_KEY, key)
            .apply()
    }

    fun get(): String {
        return preferences.getString(API_KEY, "")!!
    }

    private fun checkValueAlreadySaved(key: String): Boolean {
        return preferences.contains(API_KEY) && get() == key
    }
}
