package com.asociateapp.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asociateapp.pixabaysearcher.config.ConfigBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val testApiKey = "16364083-5d65dcbe76548cde9ed7b94c0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_api_key.setText(testApiKey)

        btn_start_image_gallery.setOnClickListener {
            val configBuilder = ConfigBuilder(this, testApiKey)
            configBuilder.setActivityTitle(getActivityTitle())
                .setSearchTerm(getSearchTerm())
                .selectedImageUriIsExpected(expectingUri())
                .showActivityUpButton(showUpButton())
            startActivity(configBuilder.build())
        }
    }

    private fun getActivityTitle() = et_gallery_title.text.toString()

    private fun getSearchTerm() = et_search_term.text.toString()

    private fun expectingUri() = cb_expecting_result.isChecked

    private fun showUpButton() = cb_show_back.isChecked
}
