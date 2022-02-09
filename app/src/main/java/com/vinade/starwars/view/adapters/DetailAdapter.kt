package com.vinade.starwars.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinade.starwars.databinding.FilmRecyclerViewBinding
import com.vinade.starwars.model.Film

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    private var items = mutableListOf<Film>()

    class ViewHolder(val binding: FilmRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FilmRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.filmTitle.text = item.title
    }

    override fun getItemCount()= items.size

    fun setAdapter(films: List<Film>){
        items = films as MutableList<Film>
        notifyDataSetChanged()
    }

}