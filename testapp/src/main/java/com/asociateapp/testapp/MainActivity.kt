package com.asociateapp.testapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration.Companion.IMAGE_GALLERY_RC
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration.Companion.IMAGE_GALLERY_RESULT_EXTRA
import com.asociateapp.pixabaysearcher.config.imagegallery.ImageGalleryFlow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val testApiKey = "16364083-5d65dcbe76548cde9ed7b94c0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_api_key.setText(testApiKey)

        btn_start_image_gallery.setOnClickListener {
            val imageGalleryConfig = buildGalleryConfig()
            ImageGalleryFlow().startFlow(imageGalleryConfig, this)
        }
    }

    private fun buildGalleryConfig() = GalleryConfiguration(
        testApiKey,
        getSearchTerm(),
        title = getActivityTitle(),
        showUpButton = showUpButton(),
        returnResult = expectingUri()
    )

    private fun getActivityTitle() = et_gallery_title.text.toString()

    private fun getSearchTerm() = et_search_term.text.toString()

    private fun expectingUri() = cb_expecting_result.isChecked

    private fun showUpButton() = cb_show_back.isChecked

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_GALLERY_RC && resultCode == Activity.RESULT_OK) {
            val uri = data?.getStringExtra(IMAGE_GALLERY_RESULT_EXTRA)

            tv_uri_result_header.visibility = View.VISIBLE
            tv_uri_result.visibility = View.VISIBLE
            tv_uri_result.text = uri

            result.setImageURI(Uri.parse(uri))
        }
    }
}
