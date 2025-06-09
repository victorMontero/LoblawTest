package com.example.loblawtest.data.repository

import com.example.loblawtest.data.ApiService
import com.example.loblawtest.data.Product
import com.example.loblawtest.data.Rating
import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

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
        val fakeApiResponse = Response.success(fakeProducts)
        whenever(mockApiService.getOffers()).thenReturn(fakeApiResponse)

        val result = offerRepository.getOffers()

        Assert.assertTrue(result is Resource.Success)
        Assert.assertEquals(expectedOffers, result.data)
    }

    @Test
    fun getOffers_apiError_returnsResourceError() = runTest {
        // Arrange: Simula a API lançando uma exceção
        whenever(mockApiService.getOffers()).thenThrow(RuntimeException("network error"))

        // Act: Chama a função a ser testada
        val result = offerRepository.getOffers()

        // Assert: Verifica se o resultado é um Resource.Error
        // O repositório deve capturar a exceção e empacotá-la como um erro.
        Assert.assertTrue("O resultado deveria ser Resource.Error", result is Resource.Error)
        Assert.assertNotNull("A mensagem de erro não deveria ser nula", result.message)
    }

    @Test
    fun getOffers_apiReturnsEmptyList_returnsResourceSuccessWithEmptyList() = runTest {
        // Arrange: Simula a API retornando uma resposta de sucesso com uma lista de produtos vazia
        val fakeEmptyProducts = emptyList<Product>()
        val fakeApiResponse = Response.success(fakeEmptyProducts)
        whenever(mockApiService.getOffers()).thenReturn(fakeApiResponse)

        // Act
        val result = offerRepository.getOffers()

        // Assert: O resultado deve ser um Resource.Success...
        Assert.assertTrue("O resultado deveria ser Resource.Success", result is Resource.Success)
        // ... e os dados dentro dele devem ser uma lista vazia.
        Assert.assertTrue("Os dados deveriam ser uma lista vazia", result.data?.isEmpty() == true)
    }
}