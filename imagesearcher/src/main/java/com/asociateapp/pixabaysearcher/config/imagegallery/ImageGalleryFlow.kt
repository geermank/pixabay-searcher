package com.asociateapp.pixabaysearcher.config.imagegallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.asociateapp.pixabaysearcher.config.GalleryFlow
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration.Companion.IMAGE_GALLERY_CONFIGURATION_EXTRA
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration.Companion.IMAGE_GALLERY_RC
import com.asociateapp.pixabaysearcher.presentation.imagegallery.ImageGalleryActivity

class ImageGalleryFlow : GalleryFlow {

    override fun startFlow(configuration: GalleryConfiguration, activity: AppCompatActivity) {
        val intent = Intent(activity, ImageGalleryActivity::class.java).also {
            it.putExtra(IMAGE_GALLERY_CONFIGURATION_EXTRA, configuration)
        }
        activity.startActivityForResult(intent, IMAGE_GALLERY_RC)
    }
}
