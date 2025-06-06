package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository

class GetOffersUseCase(private val offerRepository: OfferRepository) {

    suspend operator fun invoke(): List<Offer>{
        return offerRepository.getOffers()
    }
}