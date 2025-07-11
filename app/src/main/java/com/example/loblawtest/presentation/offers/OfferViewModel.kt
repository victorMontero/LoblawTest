package com.example.loblawtest.presentation.offers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.usecase.GetOffersUseCase
import com.example.loblawtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(private val getOffersUseCase: GetOffersUseCase) :
    ViewModel() {

    private val _allOffers = MutableStateFlow<List<Offer>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val offers = _allOffers.combine(_searchQuery) { offers, query ->
        if (query.isBlank()) {
            offers
        } else {
            offers.filter {
                it.productName.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }.stateIn( // Converte o Flow resultante em um StateFlow
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {
        fetchOffersFromApi()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun fetchOffersFromApi() {
        viewModelScope.launch {
           when(val result = getOffersUseCase()){
               is Resource.Success -> {
                   _allOffers.value = result.data ?: emptyList()
               }
               is Resource.Error -> {
                   Log.e("OfferViewModel", "Erro ao buscar ofertas: ${result.message}")
                    _allOffers.value = emptyList()
               }
               is Resource.Loading -> {

               }
           }
        }
    }
}