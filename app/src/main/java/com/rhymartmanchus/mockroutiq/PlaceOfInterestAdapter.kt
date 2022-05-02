package com.rhymartmanchus.mockroutiq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rhymartmanchus.mockroutiq.databinding.LayoutPlaceOfInterestBinding
import com.rhymartmanchus.mockroutiq.domain.PlaceOfInterest
import com.squareup.picasso.Picasso

class PlaceOfInterestAdapter(
    private val placeOfInterests: List<PlaceOfInterest>
) : RecyclerView.Adapter<PlaceOfInterestAdapter.ViewHolder>() {

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binder: LayoutPlaceOfInterestBinding = LayoutPlaceOfInterestBinding.bind(itemView)

        fun bind(placeOfInterest: PlaceOfInterest) {
            with(binder) {
                Picasso.get()
                    .load(placeOfInterest.imageUrl)
                    .resize(500, 400)
                    .centerCrop()
                    .into(ivImage)

                tvName.text = placeOfInterest.name
                tvCategory.text = placeOfInterest.category
                tvDescription.text = placeOfInterest.description
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_place_of_interest, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeOfInterests[position])
    }

    override fun getItemCount(): Int = placeOfInterests.size

}