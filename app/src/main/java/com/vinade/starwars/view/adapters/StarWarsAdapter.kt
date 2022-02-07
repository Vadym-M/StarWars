package com.vinade.starwars.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vinade.starwars.R
import com.vinade.starwars.model.Result
import com.vinade.starwars.util.AdapterDataType
import com.vinade.starwars.util.getResult

class StarWarsAdapter : RecyclerView.Adapter<StarWarsAdapter.ViewHolder>() {

    init {
        Log.d("debug", "AdapterCreated")
    }

    var onItemClick: ((Result) -> Unit)? = null
    private var items = mutableListOf<AdapterDataType>()

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (items[adapterPosition] is AdapterDataType.Item) {
                    onItemClick?.invoke(items[adapterPosition].getResult()!!)
                }
            }

        }
        private fun bindHeader(text: AdapterDataType.Header){
            view.findViewById<TextView>(R.id.headerText)?.text = text.header
        }
        private fun bindItem(item: AdapterDataType.Item){
            view.findViewById<TextView>(R.id.itemName)?.text = item.data.name
//            view.findViewById<TextView>(R.id.itemMass)?.text = view.context.getString(R.string.mass_d,  item.data.mass)
//            view.findViewById<TextView>(R.id.itemHeight)?.text = view.context.getString(R.string.height_d,  item.data.height)

        }


        fun bind(data : AdapterDataType){
            when(data){
                is AdapterDataType.Header -> bindHeader(data)
                is AdapterDataType.Item -> bindItem(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when(viewType){
            TYPE_ITEMS -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
            }
            TYPE_HEADER -> {
                LayoutInflater.from(parent.context).inflate(R.layout.header_recycler_view, parent, false)
            }
            else -> throw IllegalArgumentException("Invalid type")

        }
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]){
            is AdapterDataType.Header -> TYPE_HEADER
            is AdapterDataType.Item -> TYPE_ITEMS
        }
    }

    fun setAdapter(newItems: List<AdapterDataType>){
        items = newItems as MutableList<AdapterDataType>
        notifyDataSetChanged()
    }

    companion object{
        const val TYPE_ITEMS = 1
        const val TYPE_HEADER = 0
    }




}