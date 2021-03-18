package com.asociateapp.pixabaysearcher.data.api.images

import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.data.api.ApiParams

internal class ImagesApiParams(
    private val configuration: GalleryConfiguration,
    private val imagesParamsBuilder: ImagesParamsBuilder
) : ApiParams {

    var page = 1

    override fun get(): HashMap<String, Any> {
        return imagesParamsBuilder
            .setApiKey(configuration.apiKey)
            .setPage(page)
            .setEditorsChoiceParam(configuration.onlyEditorsChoice)
            .setItemsPerPage(configuration.imagesPerPage)
            .setSearchTerm(configuration.searchTerm)
            .build()
    }
}
