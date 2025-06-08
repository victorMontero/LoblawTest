package com.example.loblawtest.domain.usecase

import com.example.loblawtest.domain.model.ShoppingItem
import com.example.loblawtest.domain.repository.ShoppingListRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(item: ShoppingItem) {
        repository.insertItem(item)
    }
}