package com.example.vetterappplus.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.vetterappplus.R

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val images = intArrayOf(R.drawable.dog3,
        R.drawable.reset,
        R.drawable.dog,
        R.drawable.dog2,
        R.drawable.background1,
        R.drawable.cap4
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
        }
    }



}