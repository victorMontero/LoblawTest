package com.example.loblawtest.presentation.offers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.usecase.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(private val getOffersUseCase: GetOffersUseCase) :
    ViewModel() {

    private var _offers: MutableStateFlow<List<Offer>> = MutableStateFlow(emptyList())
    val offers: StateFlow<List<Offer>> = _offers.asStateFlow()

    init {
        fetchOffersFromApi()
    }

    private fun fetchOffersFromApi() {
        viewModelScope.launch {
            try {
                val offers: List<Offer> = getOffersUseCase()
                _offers.value = offers

            } catch (e: Exception) {
                Log.e("OfferViewModel", "Erro ao buscar ofertas da API: ${e.message}", e)
                _offers.value = emptyList()
            }
        }
    }
}