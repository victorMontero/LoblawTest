package com.example.loblawtest.domain.repository

import com.example.loblawtest.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun insertItem(item: ShoppingItem)
    suspend fun deleteItem(item: ShoppingItem)
    fun getAllItems(): Flow<List<ShoppingItem>>
}