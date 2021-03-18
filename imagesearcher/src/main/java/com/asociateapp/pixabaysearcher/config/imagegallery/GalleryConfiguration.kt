package com.asociateapp.pixabaysearcher.config.imagegallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryConfiguration(
    val apiKey: String,
    val searchTerm: String = Defaults.SEARCH_TERM,
    val onlyEditorsChoice: Boolean = Defaults.USE_EDITORS_CHOICE,
    val imagesPerPage: Int = Defaults.IMAGES_PER_PAGE,
    val title: String = Defaults.GALLERY_TITLE,
    val showUpButton: Boolean = Defaults.SHOW_UP_BUTTON,
    val returnResult: Boolean = Defaults.RETURN_RESULT
) : Parcelable {

    companion object {
        const val IMAGE_GALLERY_RC = 122
        const val IMAGE_GALLERY_RESULT_EXTRA = "IMAGE_GALLERY_RESULT_EXTRA_KEY"
        const val IMAGE_GALLERY_CONFIGURATION_EXTRA = "IMAGE_GALLERY_CONFIGURATION_EXTRA"
    }
}
