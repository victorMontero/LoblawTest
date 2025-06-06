package com.example.loblawtest.di

import com.example.loblawtest.data.repository.OfferRepositoryImpl
import com.example.loblawtest.domain.repository.OfferRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindOfferRepository(offerRepositoryImpl: OfferRepositoryImpl): OfferRepository
}