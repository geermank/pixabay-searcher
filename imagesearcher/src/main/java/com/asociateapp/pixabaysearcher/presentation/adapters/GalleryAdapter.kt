package com.asociateapp.pixabaysearcher.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.utils.ImageLoader
import kotlinx.android.synthetic.main.item_image.view.*

internal class GalleryAdapter(private val images: List<Image>) : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    interface OnImageClickedListener {
        fun onImageClick(image: Image)
    }

    private var clickListener: OnImageClickedListener? = null

    fun setImageClickListener(clickListener: OnImageClickedListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        return GalleryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.bind(images[position])
    }

    inner class GalleryHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(image: Image) {
            loadImage(image.previewURL)
            setClickListener(image)
        }

        private fun loadImage(url: String) {
            ImageLoader.load(url, itemView.iv_image)
        }

        private fun setClickListener(image: Image) {
            itemView.setOnClickListener { clickListener?.onImageClick(image) }
        }
    }
}