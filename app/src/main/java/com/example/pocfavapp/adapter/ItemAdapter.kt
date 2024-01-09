package com.example.pocfavapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocfavapp.databinding.ViewItemBinding

data class Item(val id: Int, val name: String)

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var items: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.textViewName.text = item.name

            binding.rootItem.setOnClickListener {
                onClick?.invoke(item.id)
            }
        }
    }
}
