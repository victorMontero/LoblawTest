package com.example.loblawtest.presentation.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loblawtest.domain.model.ShoppingItem
import com.example.loblawtest.domain.usecase.AddItemUseCase
import com.example.loblawtest.domain.usecase.DeleteItemUseCase
import com.example.loblawtest.domain.usecase.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    // Expõe o Flow de itens para a UI.
    // O stateIn o converte em um StateFlow, que "guarda" o último valor emitido.
    val shoppingItems = getItemsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onAddItem(name: String, quantity: String) {
        // Validação simples de entrada
        if (name.isBlank() || quantity.isBlank()) {
            return
        }

        viewModelScope.launch {
            val item = ShoppingItem(name = name, quantity = quantity.toIntOrNull() ?: 1)
            addItemUseCase(item)
        }
    }

    fun onDeleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            deleteItemUseCase(item)
        }
    }
}