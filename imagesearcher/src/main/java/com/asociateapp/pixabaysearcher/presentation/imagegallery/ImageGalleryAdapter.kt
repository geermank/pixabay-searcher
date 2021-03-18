package com.asociateapp.pixabaysearcher.presentation.imagegallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.images.ImageLoader
import com.asociateapp.pixabaysearcher.presentation.recyclerview.ClickableAdapter
import com.asociateapp.pixabaysearcher.presentation.recyclerview.ClickableViewHolder
import com.asociateapp.pixabaysearcher.presentation.recyclerview.OnListItemClickListener
import kotlinx.android.synthetic.main.item_image.view.*

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageDto>() {
    override fun areItemsTheSame(oldItem: ImageDto, newItem: ImageDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageDto, newItem: ImageDto): Boolean {
        return oldItem == newItem
    }
}

internal class ImageGalleryAdapter(
    listener: OnListItemClickListener<ImageDto>
) : ClickableAdapter<ImageDto, ImageGalleryHolder>(listener, DIFF_CALLBACK) {

    override fun createNewViewHolder(
        parent: ViewGroup,
        clickListener: OnListItemClickListener<ImageDto>
    ) = ImageGalleryHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false),
        clickListener
    )
}

class ImageGalleryHolder(
    view: View,
    clickListener: OnListItemClickListener<ImageDto>
) : ClickableViewHolder<ImageDto>(view, clickListener) {

    override fun bind(data: ImageDto) {
        loadImage(data.previewURL)
    }

    private fun loadImage(url: String) {
        ImageLoader.load(url, itemView.iv_image)
    }
}
