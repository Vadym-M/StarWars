package com.vinade.starwars.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinade.starwars.databinding.ItemRecyclerViewBinding
import com.vinade.starwars.model.Result

class StarWarsAdapter : RecyclerView.Adapter<StarWarsAdapter.ViewHolder>() {

    var onItemClick: ((Result) -> Unit)? = null
    var items: List<Result> = emptyList()

    inner class ViewHolder(val binding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            itemName.text = item.name
            itemMass.text = item.mass
            itemHeight.text = item.height
        }
    }

    override fun getItemCount() = items.size

    fun setAdapter(newItems: List<Result>){
        items = newItems
        notifyDataSetChanged()
    }

}