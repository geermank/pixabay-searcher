package com.asociateapp.pixabaysearcher.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asociateapp.pixabaysearcher.Configurator
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.api.PixabayApi
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import com.asociateapp.pixabaysearcher.presentation.models.Default
import com.asociateapp.pixabaysearcher.presentation.models.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GalleryViewModel(
    private val repository: ImagesRepository,
    private val galleryConfig: Configurator
) : ViewModel() {

    val images = MutableLiveData<State<List<Image>>>()

    init {
        images.value = Default(emptyList(), 0)
    }

    @SuppressLint("CheckResult")
    fun search(term: String) {
        repository.searchImagesByTerm(PixabayApi.API_KEY, galleryConfig.getImagesPerPage(), term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onImagesReceived, this::onError)
    }

    private fun onImagesReceived(response: Response<List<Image>>) {
        updateCurrentData(response.hits)
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
    }

    private fun updateCurrentData(newImages: List<Image>) {
        val loadedImages = currentData().toMutableList()
        loadedImages.addAll(newImages)

        val newPage = currentPage() + 1

        this.images.value = Default(loadedImages, newPage)
    }

    private fun currentPage() = this.images.value?.page ?: 0

    private fun currentData() = this.images.value?.data ?: emptyList()

    fun getActivityTitle() = galleryConfig.getActivityTitle()

    fun showUpButton() = galleryConfig.showUpButton()
}