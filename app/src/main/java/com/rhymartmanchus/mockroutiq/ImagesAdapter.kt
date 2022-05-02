package com.rhymartmanchus.mockroutiq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rhymartmanchus.mockroutiq.databinding.LayoutRouteImageBinding
import com.squareup.picasso.Picasso

class ImagesAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {


    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binder: LayoutRouteImageBinding = LayoutRouteImageBinding.bind(itemView)

        fun bind(url: String) {
            Picasso.get()
                .load(url)
                .resize(500, 400)
                .centerCrop()
                .into(binder.ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_route_image, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

}