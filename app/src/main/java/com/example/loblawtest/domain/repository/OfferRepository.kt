package com.example.loblawtest.domain.repository

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.util.Resource

interface OfferRepository {
    suspend fun getOffers(): Resource<List<Offer>>
}