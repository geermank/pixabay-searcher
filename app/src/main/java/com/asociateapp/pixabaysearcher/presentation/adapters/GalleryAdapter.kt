package com.asociateapp.pixabaysearcher.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.data.models.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class GalleryAdapter(private val images: List<Image>) : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        return GalleryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.bind(images[position])
    }

    inner class GalleryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: Image) {
            Picasso.get().load(image.previewURL).into(itemView.iv_image)
        }
    }

}