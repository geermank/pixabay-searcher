package com.asociateapp.pixabaysearcher.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.api.ImagesApi
import com.asociateapp.pixabaysearcher.data.api.RetrofitWrapper
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.presentation.adapters.GalleryAdapter
import com.asociateapp.pixabaysearcher.presentation.models.Default
import com.asociateapp.pixabaysearcher.presentation.models.Error
import com.asociateapp.pixabaysearcher.presentation.viewmodel.GalleryViewModel
import com.asociateapp.pixabaysearcher.presentation.viewmodel.getViewModel
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : BaseActivity() {

    private val viewModel by lazy {
        getViewModel {
            val imagesApi = ImagesApi(RetrofitWrapper().getClient())
            val repository = ImagesRepository(imagesApi)
            GalleryViewModel(repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        toolbarSetUp()

        viewModel.search("Libros")
        viewModel.images.observe(this, Observer {
            pb.visibility = View.GONE
            if (it is Default) {
                setUpImagesList(it.data)
            } else if (it is Error) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toolbarSetUp() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Elegí un imagen"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpImagesList(images: List<Image>) {
        val adapter = GalleryAdapter(images)
        val layoutManager = GridLayoutManager(this, 3)

        rv_images.layoutManager = layoutManager
        rv_images.adapter = adapter
    }

}
