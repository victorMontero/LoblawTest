package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.ShoppingItem
import com.example.loblawtest.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    operator fun invoke(): Flow<List<ShoppingItem>> {
        return repository.getAllItems()
    }
}