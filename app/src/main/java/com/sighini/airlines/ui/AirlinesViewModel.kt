package com.sighini.airlines.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sighini.airlines.data.Airline
import com.sighini.airlines.data.AirlinesRepository
import com.sighini.airlines.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class AirlinesViewModel(private val repository: AirlinesRepository) : ViewModel() {

    val items = Pager(
        PagingConfig(
            pageSize = 50,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        repository.getAllAirlines()
    }.flow


    private val mutableSelectedAirline = MutableLiveData<Airline>()
    val selectedAirline: LiveData<Airline> get() = mutableSelectedAirline

    fun selectItem(airline: Airline) {
        mutableSelectedAirline.value = airline
    }

    fun updateFavorite(code: String, favorite: Boolean?) {
        viewModelScope.launch {
            repository.updateFavorite(code, favorite)
        }
    }

    init {
        viewModelScope.launch {
            repository.fetchAirlines()
        }
    }
    companion object {
        val FACTORY = singleArgViewModelFactory(::AirlinesViewModel)
    }
}