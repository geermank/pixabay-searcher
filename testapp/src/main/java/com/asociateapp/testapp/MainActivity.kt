package com.asociateapp.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asociateapp.pixabaysearcher.Configurator
import com.asociateapp.pixabaysearcher.presentation.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start_image_gallery.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra(Configurator.PIXABAY_API_KEY, "16364083-5d65dcbe76548cde9ed7b94c0")
            intent.putExtra(Configurator.PIXABAY_SEARCH_TERM, "Negocios")
            intent.putExtra(Configurator.ACTIVITY_TITLE, "Galeria de fotos")
            intent.putExtra(Configurator.ACTIVITY_SHOW_UP_BUTTON, true)
            startActivity(intent)
        }
    }
}
