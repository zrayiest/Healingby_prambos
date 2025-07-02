package com.jimi15.uas_nmp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimi15.uas_nmp.databinding.ItemFavoriteCardBinding
import com.squareup.picasso.Picasso

class FavoriteAdapter(val favorites: ArrayList<Location>):
RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    class FavoriteViewHolder(val binding: ItemFavoriteCardBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]

        holder.binding.txtName.text = favorite.name
        holder.binding.txtCategory.text = favorite.category

        Picasso.get()
            .load(favorite.image_url)
            .into(holder.binding.imgPhoto)

        holder.binding.cardFav.setOnClickListener{
            val intent = Intent(holder.itemView.context, HealingDetailsActivity::class.java)
            intent.putExtra("id_location", favorite.id_location)
            intent.putExtra("name", favorite.name)
            intent.putExtra("image_url", favorite.image_url)
            intent.putExtra("short_description", favorite.short_description)
            intent.putExtra("category", favorite.category)
            intent.putExtra("full_description", favorite.full_description)
            intent.putExtra("address", favorite.address)
            intent.putExtra("operating_hours", favorite.operating_hours)
            intent.putExtra("FROM_FAVORITES", true)
            holder.itemView.context.startActivity(intent)
        }
    }
}
