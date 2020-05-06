package com.asociateapp.testapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asociateapp.pixabaysearcher.config.Configurator
import com.asociateapp.pixabaysearcher.config.PixabayConfigBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val testApiKey = "16364083-5d65dcbe76548cde9ed7b94c0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_api_key.setText(testApiKey)

        btn_start_image_gallery.setOnClickListener {
            val configBuilder = PixabayConfigBuilder(this, testApiKey)
            configBuilder.apply {
                setActivityTitle(getActivityTitle())
                setSearchTerm(getSearchTerm())
                selectedImageUriIsExpected(expectingUri())
                showActivityUpButton(showUpButton())
            }
            startActivityForResult(configBuilder.createIntent(), Configurator.IMAGE_SEARCHER_RC)
        }
    }

    private fun getActivityTitle() = et_gallery_title.text.toString()

    private fun getSearchTerm() = et_search_term.text.toString()

    private fun expectingUri() = cb_expecting_result.isChecked

    private fun showUpButton() = cb_show_back.isChecked

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Configurator.IMAGE_SEARCHER_RC && resultCode == Activity.RESULT_OK) {
            val uri = data?.getStringExtra(Configurator.IMAGE_SEARCHER_SELECTED_IMAGE_URI)

            tv_uri_result_header.visibility = View.VISIBLE
            tv_uri_result.visibility = View.VISIBLE
            tv_uri_result.text = uri
        }

    }

}
