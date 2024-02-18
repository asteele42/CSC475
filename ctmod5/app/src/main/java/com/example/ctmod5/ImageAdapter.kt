package com.example.ctmod5

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val images: List<GalleryItem>) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val galleryItem: GalleryItem = images[position]
        Glide.with(holder.itemView.context).load(galleryItem.imagePath).into(holder.imageView)

        // Set image dimensions programmatically
        val imageSize = 300 // Example size in pixels
        holder.imageView.layoutParams.width = imageSize
        holder.imageView.layoutParams.height = imageSize
        holder.imageView.requestLayout()
        holder.imageView.setOnClickListener {
            val intent = Intent(holder.itemView.context, FullScreenImageActivity::class.java)
            intent.putExtra("imagePath", galleryItem.imagePath)
            holder.itemView.context.startActivity(intent)
        }

    }



    override fun getItemCount(): Int = images.size
}
