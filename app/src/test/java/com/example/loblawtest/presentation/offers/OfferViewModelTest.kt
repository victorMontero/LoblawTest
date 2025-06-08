package com.example.loblawtest.presentation.offers

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.usecase.GetOffersUseCase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class OfferViewModelTest {

    private lateinit var mockGetOffersUseCase: GetOffersUseCase
    private lateinit var offerViewModel: OfferViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockGetOffersUseCase = Mockito.mock()
    }

    @Test
    fun `init_useCaseSuccess_updatesStateWithOffers`() = runTest {
        // Arrange
        val fakeOffers = listOf(Offer(1, "Offer 1", "Desc 1", 100.0, ""))
        whenever(mockGetOffersUseCase.invoke()).thenReturn(fakeOffers)

        // Act
        offerViewModel = OfferViewModel(mockGetOffersUseCase)

        // Assert
        testDispatcher.scheduler.advanceUntilIdle() // Executa a corrotina do init
        val offersState = offerViewModel.offers.first()

        TestCase.assertEquals(fakeOffers, offersState)
        verify(mockGetOffersUseCase).invoke()
    }

    @Test
    fun `init_useCaseError_updatesStateWithEmptyList`() = runTest {
        // Arrange
        whenever(mockGetOffersUseCase.invoke()).thenThrow(RuntimeException("Use case error"))

        // Act
        offerViewModel = OfferViewModel(mockGetOffersUseCase)

        // Assert
        testDispatcher.scheduler.advanceUntilIdle()
        val offersState = offerViewModel.offers.first()

        TestCase.assertEquals(emptyList<Offer>(), offersState)
        verify(mockGetOffersUseCase).invoke()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}