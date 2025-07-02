package com.jimi15.uas_nmp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimi15.uas_nmp.databinding.ItemHealingCardBinding
import com.squareup.picasso.Picasso

class LocationAdapter(val locations: ArrayList<Location>):
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(val binding: ItemHealingCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        val binding = ItemHealingCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]

        holder.binding.txtName.text = location.name
        holder.binding.txtCategory.text = location.category
        holder.binding.txtShortDesc.text = location.short_description

        Picasso.get()
            .load(location.image_url)
            .into(holder.binding.imgLocation)

        holder.binding.btnReadMore.setOnClickListener{
            val intent = Intent(holder.itemView.context, HealingDetailsActivity::class.java)
            intent.putExtra("id_location", location.id_location)
            intent.putExtra("name", location.name)
            intent.putExtra("image_url", location.image_url)
            intent.putExtra("short_description", location.short_description)
            intent.putExtra("category", location.category)
            intent.putExtra("full_description", location.full_description)
            intent.putExtra("address", location.address)
            intent.putExtra("operating_hours", location.operating_hours)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

}