package com.example.loblawtest.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getOffers(): Response<List<Product>>
}