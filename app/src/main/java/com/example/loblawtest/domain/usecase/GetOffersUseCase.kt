package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val offerRepository: OfferRepository) {

    suspend operator fun invoke(): List<Offer> {
        return offerRepository.getOffers()
    }
}