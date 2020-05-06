package com.asociateapp.pixabaysearcher.domain

import com.asociateapp.pixabaysearcher.config.Configurator
import com.asociateapp.pixabaysearcher.config.PixabayConfigurator
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import io.reactivex.Single

internal class PixabayGalleryUserCase(private val imagesRepository: ImagesRepository) : GalleryUseCase {

    override fun getImages(config: Configurator): Single<Response<List<Image>>> {
        val configurator = config as PixabayConfigurator
        return imagesRepository.searchImagesByTerm(configurator.getApiKey(),
            configurator.getImagesPerPage(), configurator.getSearchTerm())
    }
}