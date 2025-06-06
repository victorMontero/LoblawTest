package com.example.loblawtest.presentation.offers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferListScreen(offerViewModel: OfferViewModel = hiltViewModel()) {
    val offers = offerViewModel.offers.collectAsStateWithLifecycle().value

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("oferttionhas") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    ) { innerPadding ->
        if (offers.isEmpty()) {
            Text(
                text = "no offers for you",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(offers, key = { offer -> offer.id }) { offer ->
                    OfferItemCard(offer = offer)
                }
            }
        }
    }
}