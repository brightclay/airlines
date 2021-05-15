package com.sighini.airlines.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sighini.airlines.data.Airline
import com.sighini.airlines.data.AirlinesRepository
import com.sighini.airlines.databinding.ItemListContentBinding

class AirlinesAdapter(private val cl: AirlineClickListener): PagingDataAdapter<Airline, AirlinesAdapter.AirlineViewHolder>(DIFF_CALLBACK) {

   // var onItemClick: ((Airline) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Airline>() {
            override fun areItemsTheSame(oldItem: Airline, newItem: Airline) = oldItem.code == newItem.code
            override fun areContentsTheSame(oldItem: Airline, newItem: Airline) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AirlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirlineViewHolder, position: Int) {
        val airline = getItem(position)
        airline?.let { holder.bind(it, cl.clickListener) }
    }

    inner class AirlineViewHolder(val binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(airline: Airline, clickListener: (Airline) -> Unit) {
            binding.name.text = airline.usName
            binding.code.text = airline.code
            airline.logoURL?.let {
                Glide.with(binding.logo.context)
                    .load(AirlinesRepository.BASE_URL + airline.logoURL)
                    .into(binding.logo)
            }
            binding.listItemContainer.setOnClickListener { clickListener(airline) }
        }
    }

    data class AirlineClickListener(val clickListener: (Airline) -> Unit)
}