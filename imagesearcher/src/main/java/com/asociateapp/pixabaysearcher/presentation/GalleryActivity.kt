package com.asociateapp.pixabaysearcher.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.asociateapp.pixabaysearcher.Configurator
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.api.ImagesApi
import com.asociateapp.pixabaysearcher.data.api.RetrofitWrapper
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.presentation.adapters.GalleryAdapter
import com.asociateapp.pixabaysearcher.presentation.dialogs.ImageDetailDialog
import com.asociateapp.pixabaysearcher.presentation.models.Default
import com.asociateapp.pixabaysearcher.presentation.models.Error
import com.asociateapp.pixabaysearcher.presentation.viewmodel.GalleryViewModel
import com.asociateapp.pixabaysearcher.presentation.viewmodel.getViewModel
import com.asociateapp.pixabaysearcher.utils.GalleryDecorator
import com.asociateapp.pixabaysearcher.utils.changeVisibility
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : BaseActivity(), GalleryAdapter.OnImageClickedListener {

    private val viewModel by lazy {
        getViewModel {
            val imagesApi = ImagesApi(RetrofitWrapper().getClient())
            GalleryViewModel(ImagesRepository(imagesApi), Configurator(intent))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        toolbarSetUp()
        viewModel.search(viewModel.getSearchTerm())
        viewModel.images.observe(this, Observer {
            pb.changeVisibility(it.data.isEmpty())
            if (it is Default) {
                setUpImagesList(it.data)
            } else if (it is Error) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toolbarSetUp() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = viewModel.getActivityTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(viewModel.showUpButton())
    }

    private fun setUpImagesList(images: List<Image>) {
        val galleryAdapter = GalleryAdapter(images).also { it.setImageClickListener(this) }
        val gridLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.gallery_image_spans))

        rv_images.apply {
            layoutManager = gridLayoutManager
            adapter = galleryAdapter
            addItemDecoration(getGalleryDecorator())
        }
    }

    private fun getGalleryDecorator() = GalleryDecorator(
        resources.getDimensionPixelSize(R.dimen.gallery_image_spacing),
        resources.getInteger(R.integer.gallery_image_spans)
    )

    override fun onImageClick(image: Image) {
        ImageDetailDialog.newInstance(image.largeImageURL).show(supportFragmentManager, ImageDetailDialog.TAG)
    }

}