package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.ShoppingItem
import com.example.loblawtest.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(item: ShoppingItem) {
        repository.deleteItem(item)
    }
}