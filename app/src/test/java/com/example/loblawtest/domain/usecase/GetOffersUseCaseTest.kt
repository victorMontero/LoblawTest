package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.repository.OfferRepository
import com.example.loblawtest.domain.model.Offer
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals
import org.mockito.kotlin.verify


class GetOffersUseCaseTest {

    private lateinit var mockOfferRepository: OfferRepository
    private lateinit var getOffersUseCase: GetOffersUseCase

    @Before
    fun setup(){
        mockOfferRepository = mock()
        getOffersUseCase = GetOffersUseCase(mockOfferRepository)
    }

    @Test
    fun invoke_repositorySuccess_returnsData() = runTest {
        // Arrange
        val fakeOffers = listOf(Offer(1, "Test", "Test desc", 1.0))
        whenever(mockOfferRepository.getOffers()).thenReturn(fakeOffers)

        // Act
        val result = getOffersUseCase()

        // Assert
        assertEquals(fakeOffers, result)
        verify(mockOfferRepository).getOffers()
    }

    @Test
    fun invoke_repositoryFails_propagatesException() = runTest {
        // Arrange
        val exception = RuntimeException("Repo error")
        whenever(mockOfferRepository.getOffers()).thenThrow(exception)
        var caughtException: Throwable? = null

        // Act
        try {
            getOffersUseCase()
        } catch (e: Throwable) {
            caughtException = e
        }

        // Assert
        assertEquals(exception, caughtException)
    }
}