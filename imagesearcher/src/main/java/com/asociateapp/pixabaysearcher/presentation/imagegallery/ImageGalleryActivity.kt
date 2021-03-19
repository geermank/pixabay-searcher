package com.asociateapp.pixabaysearcher.presentation.imagegallery

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.databinding.ActivityImageGalleryBinding
import com.asociateapp.pixabaysearcher.presentation.BaseActivity
import com.asociateapp.pixabaysearcher.presentation.imagedetail.ImageDetailDialog
import com.asociateapp.pixabaysearcher.presentation.recyclerview.OnListItemClickListener
import com.asociateapp.pixabaysearcher.utils.extensions.changeVisibility
import com.asociateapp.pixabaysearcher.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageGalleryActivity : BaseActivity(), OnListItemClickListener<ImageDto>, ImageDetailDialog.OnImageInteractListener {

    private val viewModel: GalleryViewModel by viewModels()
    private val imagesAdapter = ImageGalleryAdapter(this)

    private lateinit var binding: ActivityImageGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarSetUp()
        setUpImagesRecyclerView()
        observeImages()
    }

    private fun toolbarSetUp() {
        viewModel.toolbarConfiguration.observe(this) {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = it.title
            supportActionBar?.setDisplayHomeAsUpEnabled(it.showBack)
        }
    }

    private fun setUpImagesRecyclerView() {
        binding.rvImages.apply {
            layoutManager = GridLayoutManager(
                this@ImageGalleryActivity,
                resources.getInteger(R.integer.gallery_image_spans)
            )
            adapter = imagesAdapter
            addItemDecoration(getGalleryDecorator())
        }
    }

    private fun observeImages() {
        viewModel.images.observe(this) {
            hideLoadingIfNotEmpty(it)
            imagesAdapter.submitList(it)
        }
    }

    override fun onItemClick(item: ImageDto) {
        ImageDetailDialog.newInstance(item.largeImageURL, true)
            .show(supportFragmentManager, ImageDetailDialog.TAG)
    }

    override fun onImageDownloaded(uri: Uri) {
        toast(R.string.image_gallery_download_success)
        if (viewModel.expectingUriResult()) {
            val extras = Bundle().also {
                it.putString(GalleryConfiguration.IMAGE_GALLERY_RESULT_EXTRA, uri.toString())
            }
            deliverResult(extras)
        }
    }

    private fun getGalleryDecorator() = ImageGalleryDecorator(
        resources.getDimensionPixelSize(R.dimen.gallery_image_spacing),
        resources.getInteger(R.integer.gallery_image_spans)
    )

    private fun hideLoadingIfNotEmpty(items: PagedList<ImageDto>) {
        binding.pb.changeVisibility(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isHomeMenuItem(item)) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
