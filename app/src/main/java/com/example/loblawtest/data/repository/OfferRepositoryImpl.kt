package com.example.loblawtest.data.repository

import android.util.Log
import com.example.loblawtest.data.ApiService
import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository

class OfferRepositoryImpl(private val apiService: ApiService): OfferRepository {
    override suspend fun getOffers(): List<Offer> {
        return try {
            val products = apiService.getOffers()

            products.map { product ->
                Offer(
                    id = product.id,
                    productName =  product.title,
                    description = product.description,
                    points = product.price
                )
            }
        } catch (e: Exception){
            Log.e("OfferRepositoryImpl", "Erro ao buscar ofertas da API: ${e.message}", e)
            emptyList()
        }
    }


}