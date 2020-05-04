package com.asociateapp.pixabaysearcher.data.api

open class BaseApi {

    private val apiKeyParam = "key"
    private val editorsChoiceParam = "editors_choice"
    private val itemsPerPageParam = "per_page"
    private val searchTermParam = "q"

    protected fun buildBaseParams(apiKey: String, itemsPerPage: Int, searchTerm: String) = HashMap<String, String>().apply {
        this[apiKeyParam] = apiKey
        this[editorsChoiceParam] = "true"
        this[itemsPerPageParam] = itemsPerPage.toString()
        this[searchTermParam] = searchTerm
    }

}