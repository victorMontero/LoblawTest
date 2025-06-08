package com.example.loblawtest.data.repository

import com.example.loblawtest.data.ApiService
import com.example.loblawtest.data.Product
import com.example.loblawtest.data.Rating
import com.example.loblawtest.domain.model.Offer
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class OfferRepositoryImplTest {

    private lateinit var mockApiService: ApiService
    private lateinit var offerRepository: OfferRepositoryImpl

    @Before
    fun setUp() {
        mockApiService = mock()
        offerRepository = OfferRepositoryImpl(mockApiService)
    }

    @Test
    fun getOffers_apiSuccess_returnsMappedOfferList() = runTest {
        val fakeProducts = listOf(
            Product(1, "Product 1", 10.0, "Desc 1", "Category 1", "img1", Rating(4.5, 100)),
            Product(2, "Product 2", 20.0, "Desc 2", "Category 2", "img2", Rating(4.0, 50))
        )
        val expectedOffers = listOf(
            Offer(1, "Product 1", "Desc 1", 10.0, "img1"),
            Offer(2, "Product 2", "Desc 2", 20.0, "img2")
        )
        whenever(mockApiService.getOffers()).thenReturn(fakeProducts)

        val result = offerRepository.getOffers()

        Assert.assertEquals(expectedOffers, result)
    }

    @Test
    fun getOffers_apiError_returnsEmptyList() = runTest {
        whenever(mockApiService.getOffers()).thenThrow(RuntimeException("network error"))
        val expectedEmptyList = emptyList<Offer>()

        val result = offerRepository.getOffers()

        Assert.assertEquals(expectedEmptyList, result)

    }

    @Test
    fun getOffers_apiReturnsEmptyList_returnsEmptyList() = runTest {
        val fakeEmptyProducts = emptyList<Product>()
        whenever(mockApiService.getOffers()).thenReturn(fakeEmptyProducts)
        val expectedEmptyList = emptyList<Offer>()

        val result = offerRepository.getOffers()

        Assert.assertEquals(expectedEmptyList, result)
    }
}