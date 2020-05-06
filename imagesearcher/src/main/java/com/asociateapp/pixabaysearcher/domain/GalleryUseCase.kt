package com.asociateapp.pixabaysearcher.domain

import com.asociateapp.pixabaysearcher.config.Configurator
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import io.reactivex.Single

internal interface GalleryUseCase {
    fun getImages(config: Configurator): Single<Response<List<Image>>>
}