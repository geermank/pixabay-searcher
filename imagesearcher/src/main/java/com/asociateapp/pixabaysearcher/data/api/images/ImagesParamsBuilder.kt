package com.asociateapp.pixabaysearcher.data.api.images

private const val EDITORS_CHOICE_PARAM = "editors_choice"
private const val ITEMS_PER_PAGE_PARAM = "per_page"
private const val SEARCH_TERM = "q"
private const val PAGE = "page"
private const val API_KEY = "key"

internal class ImagesParamsBuilder {

    private val constraints = HashMap<String, Any>()

    fun setApiKey(key: String): ImagesParamsBuilder {
        constraints[API_KEY] = key
        return this
    }

    fun setPage(page: Int): ImagesParamsBuilder {
        constraints[PAGE] = page
        return this
    }

    fun setEditorsChoiceParam(editorsChoice: Boolean): ImagesParamsBuilder {
        constraints[EDITORS_CHOICE_PARAM] = editorsChoice.toString()
        return this
    }

    fun setItemsPerPage(itemsPerPage: Int): ImagesParamsBuilder {
        constraints[ITEMS_PER_PAGE_PARAM] = itemsPerPage
        return this
    }

    fun setSearchTerm(searchTerm: String): ImagesParamsBuilder {
        constraints[SEARCH_TERM] = searchTerm
        return this
    }

    fun build() = constraints
}
