package com.example.loblawtest.presentation.offers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loblawtest.domain.model.Offer

@Composable
fun OfferItemCard(offer: Offer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(modifier = Modifier.padding(16.dp)) {

            AsyncImage(
                model = offer.imgUrl,
                contentDescription = offer.productName,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = offer.productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = offer.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Points: ${offer.points}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfferItemCardPreview() {
    MaterialTheme {
        OfferItemCard(
            offer = Offer(
                id = 1,
                productName = "example",
                description = "this is a product example description.",
                points = 1000.0,
                imgUrl = ""
            )
        )
    }
}
