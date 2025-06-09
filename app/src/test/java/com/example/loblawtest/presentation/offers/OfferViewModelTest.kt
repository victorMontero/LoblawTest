package com.example.loblawtest.presentation.offers

import com.example.loblawtest.domain.model.Offer
import com.example.loblawtest.domain.usecase.GetOffersUseCase
import com.example.loblawtest.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
    fun `init_useCaseSuccess_updatesStateWithOffers`() = runTest(testDispatcher) {
        // Arrange
        val fakeOffers = listOf(Offer(1, "Offer 1", "Desc 1", 100.0, ""))
        val fakeResource = Resource.Success(fakeOffers)
        whenever(mockGetOffersUseCase.invoke()).thenReturn(fakeResource)

        // Act & Assert
        // A biblioteca Turbine é a melhor forma de testar Flows.
        // O bloco .test {} coleta o flow, garantindo que ele esteja ativo.

        // Adicione esta dependência ao seu build.gradle.kts (app):
        // testImplementation("app.cash.turbine:turbine:1.1.0")

        offerViewModel = OfferViewModel(mockGetOffersUseCase)

        offerViewModel.offers.test {
            // O valor inicial é sempre a lista vazia.
            assertEquals(emptyList<Offer>(), awaitItem())

            // A corrotina do init executa e emite a lista de ofertas.
            // O Turbine espera por essa emissão.
            assertEquals(fakeOffers, awaitItem())

            // Garante que não há mais emissões.
            cancelAndIgnoreRemainingEvents()
        }

        // Verifica a interação com o mock no final.
        verify(mockGetOffersUseCase).invoke()
    }


    @Test
    fun `init_useCaseError_updatesStateWithEmptyList`() = runTest {
        // Arrange: Prepara a resposta de erro do caso de uso
        val errorMessage = "Use case error"
        val fakeResource = Resource.Error<List<Offer>>(errorMessage)
        whenever(mockGetOffersUseCase.invoke()).thenReturn(fakeResource)

        // Act: Inicializa o ViewModel
        offerViewModel = OfferViewModel(mockGetOffersUseCase)

        // Assert: Avança o dispatcher
        testDispatcher.scheduler.advanceUntilIdle()
        val offersState = offerViewModel.offers.first()

        // O ViewModel deve tratar o erro e expor uma lista vazia para a UI
        assertEquals(emptyList<Offer>(), offersState)
        verify(mockGetOffersUseCase).invoke()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}