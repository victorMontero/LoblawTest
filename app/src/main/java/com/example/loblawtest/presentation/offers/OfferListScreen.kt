package com.example.loblawtest.presentation.offers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferListScreen(
    offerViewModel: OfferViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val offers = offerViewModel.offers.collectAsStateWithLifecycle().value
    val searchQuery = offerViewModel.searchQuery.collectAsStateWithLifecycle().value


    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Ofertas") }, // Mudei o título para português
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            // Adicionamos o botão de ação aqui
            actions = {
                Button(
                    onClick = {
                        // Navega para a rota da lista de compras
                        navController.navigate("shopping_list")
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Ver Lista")
                }
            }
        )
    }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Adicionando o campo de busca
            TextField(
                value = searchQuery,
                onValueChange = offerViewModel::onSearchQueryChanged, // Referência da função
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Buscar por nome ou descrição...") },
                singleLine = true
            )

            if (offers.isEmpty()) {
                Text(
                    text = "Nenhuma oferta encontrada.",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(offers, key = { offer -> offer.id }) { offer ->
                        OfferItemCard(offer = offer)
                    }
                }
            }
        }
    }
}