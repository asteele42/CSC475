package com.example.ctmod5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.gallery_item, parent, false)) {
    var imageView: ImageView = itemView.findViewById(R.id.image)
}
