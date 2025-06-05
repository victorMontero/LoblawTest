package com.example.loblawtest.data.repository

import com.example.loblawtest.domain.Offer

interface OfferRepository {
    suspend fun getOffers(): List<Offer>
}