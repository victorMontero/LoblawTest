package com.example.loblawtest.domain.model

data class Offer(
    val id: Int,
    val productName: String,
    val description: String,
    val points: Double,
    val imgUrl: String
)