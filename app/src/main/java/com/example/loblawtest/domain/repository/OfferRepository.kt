package com.example.loblawtest.domain.repository

import com.example.loblawtest.domain.model.Offer

interface OfferRepository {
    suspend fun getOffers(): List<Offer>
}