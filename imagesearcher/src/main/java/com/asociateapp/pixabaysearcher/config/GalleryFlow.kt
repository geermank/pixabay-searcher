package com.asociateapp.pixabaysearcher.config

import androidx.appcompat.app.AppCompatActivity
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration

interface GalleryFlow {
    fun startFlow(configuration: GalleryConfiguration, activity: AppCompatActivity)
}