package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.repository.OfferRepository
import com.example.loblawtest.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class GetOffersUseCaseTest {

    private lateinit var mockOfferRepository: OfferRepository
    private lateinit var getOffersUseCase: GetOffersUseCase

    @Before
    fun setup() {
        mockOfferRepository = mock()
        getOffersUseCase = GetOffersUseCase(mockOfferRepository)
    }

    @Test
    fun `invoke_repositorySuccess_returnsResourceSuccess`() = runTest {
        // Arrange: Prepara os dados falsos e a resposta de sucesso do repositório
        val fakeOffers = listOf(Offer(1, "Test", "Test desc", 1.0, ""))
        val fakeResource = Resource.Success(fakeOffers)
        whenever(mockOfferRepository.getOffers()).thenReturn(fakeResource)

        // Act: Executa o caso de uso
        val result = getOffersUseCase()

        // Assert: Verifica se o resultado é o Resource.Success esperado
        assertTrue("O resultado deveria ser Resource.Success", result is Resource.Success)
        assertEquals(fakeOffers, result.data)
        verify(mockOfferRepository).getOffers()
    }

    @Test
    fun `invoke_repositoryError_returnsResourceError`() = runTest {
        // Arrange: Prepara a resposta de erro do repositório
        val errorMessage = "Repo error"
        val fakeResource = Resource.Error<List<Offer>>(errorMessage)
        whenever(mockOfferRepository.getOffers()).thenReturn(fakeResource)

        // Act: Executa o caso de uso
        val result = getOffersUseCase()

        // Assert: Verifica se o resultado é o Resource.Error esperado
        assertTrue("O resultado deveria ser Resource.Error", result is Resource.Error)
        assertEquals(errorMessage, result.message)
        verify(mockOfferRepository).getOffers()
    }
}