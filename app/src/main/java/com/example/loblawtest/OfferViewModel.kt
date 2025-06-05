package com.example.loblawtest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loblawtest.data.Product
import com.example.loblawtest.data.RetrofitClient
import com.example.loblawtest.data.repository.OfferRepository
import com.example.loblawtest.domain.Offer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfferViewModel(private val offerRepository: OfferRepository) : ViewModel() {

    private var _offers: MutableStateFlow<List<Offer>> = MutableStateFlow(emptyList())
    val offers: StateFlow<List<Offer>> = _offers.asStateFlow()

    init {
        fetchOffersFromApi()
    }

    private fun fetchOffersFromApi() {
            viewModelScope.launch {
                try {
                    val offers : List<Offer> = offerRepository.getOffers()
                    _offers.value = offers

                } catch (e: Exception){
                    Log.e("OfferViewModel", "Erro ao buscar ofertas da API: ${e.message}", e)
                    _offers.value = emptyList()
                }
            }
    }
}