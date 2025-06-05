package com.example.loblawtest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loblawtest.data.Product
import com.example.loblawtest.data.RetrofitClient
import com.example.loblawtest.domain.Offer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private var _offers: MutableStateFlow<List<Offer>> = MutableStateFlow(emptyList())
    val offers: StateFlow<List<Offer>> = _offers.asStateFlow()

    init {
        fetchOffersFromApi()
    }

    private fun fetchOffersFromApi() {
            viewModelScope.launch {
                try {
                    val offers : List<Product> = RetrofitClient.instance.getOffers()

                    val mappedOffers: List<Offer> = offers.map { product ->
                        Offer(
                            id = product.id,
                            productName = product.title,
                            description = product.description,
                            points = product.price
                        )
                    }
                    _offers.value = mappedOffers
                } catch (e: Exception){
                    Log.e("OfferViewModel", "Erro ao buscar ofertas da API: ${e.message}", e)
                    _offers.value = emptyList()
                }
            }
    }
}