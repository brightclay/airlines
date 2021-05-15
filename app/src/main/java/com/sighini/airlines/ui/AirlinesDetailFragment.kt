package com.sighini.airlines.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sighini.airlines.R
import com.sighini.airlines.data.Airline
import com.sighini.airlines.data.AirlinesDatabase
import com.sighini.airlines.data.AirlinesRepository
import com.sighini.airlines.data.getNetworkService
import com.sighini.airlines.databinding.ItemDetailBinding


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [AirlinesListActivity]
 * in two-pane mode (on tablets) or a [AirlinesDetailActivity]
 * on handsets.
 */
class AirlinesDetailFragment : Fragment() {

    private lateinit var airline: Airline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                airline = it.getSerializable(ARG_ITEM) as Airline
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = airline.usName
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = ItemDetailBinding.inflate(layoutInflater)
        val rootView = binding.root
        //val rootView = inflater.inflate(R.layout.item_detail, container, false)

        airline?.let {
            airline.logoURL?.let {
                Glide.with(binding.logo.context)
                        .load(AirlinesRepository.BASE_URL + airline.logoURL)
                        .into(binding.logo)
            }
            binding.name.text = airline.name
            binding.siteUrl.text = airline.site
            binding.phoneNumber.text = airline.phone
            updateFavorite(binding)
            binding.favorite.setOnClickListener(View.OnClickListener {
                airline.favorite = !airline.favorite
                updateFavorite(binding)
            })
        }

        return rootView
    }

    fun updateFavorite(binding: ItemDetailBinding) {
        if (airline.favorite) {
            binding.favorite.setBackgroundResource(R.drawable.favorite)
        } else {
            binding.favorite.setBackgroundResource(R.drawable.unfavorite)
        }
        //Persist the favorite status
    }

    companion object {
        /**
         * The fragment argument representing the Airline that this fragment
         * represents.
         */
        const val ARG_ITEM = "item"
    }
}