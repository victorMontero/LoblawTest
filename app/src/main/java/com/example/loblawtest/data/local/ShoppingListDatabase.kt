package com.example.loblawtest.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loblawtest.data.local.dao.ShoppingItemDao
import com.example.loblawtest.data.local.entity.ShoppingItemEntity

@Database(
    entities = [ShoppingItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}