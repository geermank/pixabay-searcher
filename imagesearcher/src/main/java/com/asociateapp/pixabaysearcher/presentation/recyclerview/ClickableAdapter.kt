package com.asociateapp.pixabaysearcher.presentation.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class ClickableAdapter<ItemType, VH : ClickableViewHolder<ItemType>>(
    private val clickListener: OnListItemClickListener<ItemType>,
    diffCallback: DiffUtil.ItemCallback<ItemType>
) : PagedListAdapter<ItemType, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { itemData ->
            holder.apply {
                setClickListener(itemData)
                bind(itemData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createNewViewHolder(parent, clickListener)
    }

    abstract fun createNewViewHolder(
        parent: ViewGroup,
        clickListener: OnListItemClickListener<ItemType>
    ): VH
}

abstract class ClickableViewHolder<ItemType>(
    view: View,
    private val clickListener: OnListItemClickListener<ItemType>
) : RecyclerView.ViewHolder(view) {

    fun setClickListener(data: ItemType) {
        itemView.setOnClickListener { clickListener.onItemClick(data) }
    }

    abstract fun bind(data: ItemType)
}