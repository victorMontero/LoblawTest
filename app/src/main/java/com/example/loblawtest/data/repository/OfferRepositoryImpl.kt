package com.example.loblawtest.data.repository

import android.util.Log
import com.example.loblawtest.data.ApiService
import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository
import com.example.loblawtest.util.Resource
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(private val apiService: ApiService): OfferRepository {
    override suspend fun getOffers(): Resource<List<Offer>> {
        return try {
            val response = apiService.getOffers()

            if (response.isSuccessful){
                val products = response.body()
                val offers = products?.map { product ->
                    Offer(
                        id = product.id,
                        productName = product.title,
                        description = product.description,
                        points = product.price,
                        imgUrl = product.image
                    )
                } ?: emptyList()
                Resource.Success(offers)
            } else{
                Resource.Error("Erro: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception){
            Log.e("offersRespositoryImpl", "erro ao buscar ofertas: ${e.message}", e)
            Resource.Error("falha na ocnexao")
        }
    }


}