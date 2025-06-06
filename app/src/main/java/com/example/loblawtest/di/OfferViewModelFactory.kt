package com.example.loblawtest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loblawtest.presentation.offers.OfferViewModel
import com.example.loblawtest.data.RetrofitClient
import com.example.loblawtest.domain.repository.OfferRepository
import com.example.loblawtest.data.repository.OfferRepositoryImpl
import com.example.loblawtest.domain.usecase.GetOffersUseCase

class OfferViewModelFactory : ViewModelProvider.Factory {

    private val apiService by lazy {
        RetrofitClient.instance
    }

    private val offerRepository: OfferRepository by lazy {
        OfferRepositoryImpl(apiService)
    }

    private val getOffersUseCase: GetOffersUseCase by lazy {
        GetOffersUseCase(offerRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfferViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OfferViewModel(getOffersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}