package com.example.pokemonapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapi.R
import com.example.pokemonapi.databinding.ItemsRowBinding
import com.example.pokemonapi.models.Pokemon


class ItemAdapter(private val items: List<Pokemon>):
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvURL.text = item.url

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that will add each item to
        val llMain = binding.llMain
        val tvName = binding.tvName
        val tvURL = binding.tvURL
    }
}