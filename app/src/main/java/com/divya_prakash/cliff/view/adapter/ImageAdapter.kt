package com.divya_prakash.cliff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.divya_prakash.cliff.Cliff
import com.divya_prakash.cliff.R
import com.divya_prakash.cliff.model.Item

class ImageAdapter: ListAdapter<Item, ImageAdapter.ImageViewHolder>(ImageCallbackDiff) {

    class ImageViewHolder(private val view: View, private val parent: ViewGroup) : RecyclerView.ViewHolder(view) {

        fun bind(item: Item) {
            val imageView = view.findViewById<ImageView>(R.id.image_view)
            Cliff.with(parent.context)
                .load(item.url)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ImageViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ImageCallbackDiff : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.url == newItem.url
    }

}