package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository
import com.example.loblawtest.util.Resource
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val offerRepository: OfferRepository) {

    suspend operator fun invoke(): Resource<List<Offer>> {
        return offerRepository.getOffers()
    }
}