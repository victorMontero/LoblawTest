package com.example.loblawtest.data.repository

import com.example.loblawtest.data.local.dao.ShoppingItemDao
import com.example.loblawtest.data.local.entity.ShoppingItemEntity
import com.example.loblawtest.domain.model.ShoppingItem
import com.example.loblawtest.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val dao: ShoppingItemDao
): ShoppingListRepository{

    override suspend fun insertItem(item: ShoppingItem) {
        dao.insertItem(item.toEntity())
    }

    override suspend fun deleteItem(item: ShoppingItem) {
        dao.deleteItem(item.toEntity())
    }

    override fun getAllItems(): Flow<List<ShoppingItem>> {
        return dao.getAllItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}

fun ShoppingItem.toEntity() = ShoppingItemEntity(id = id, name = name, quantity = quantity)
fun ShoppingItemEntity.toDomain() = ShoppingItem(id = id, name = name, quantity = quantity)