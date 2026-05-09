package com.example.uesanapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.model.CountryModel

val mockCountries = listOf(
    CountryModel("Colombia", 5, "https://flagcdn.com/w320/co.png"),
    CountryModel("Francia", 3, "https://flagcdn.com/w320/fr.png"),
    CountryModel("Brasil", 8, "https://flagcdn.com/w320/br.png"),
    CountryModel("España", 2, "https://flagcdn.com/w320/es.png"),
    CountryModel("Portugal", 7, "https://flagcdn.com/w320/pt.png"),
    CountryModel("Argentina", 1, "https://flagcdn.com/w320/ar.png"),
    CountryModel("Japón", 10, "https://flagcdn.com/w320/jp.png"),
    CountryModel("Perú", 50, "https://flagcdn.com/w320/pe.png"),
    )

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ranking FIFA 2026",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp)
        ) {
            items(mockCountries) { country ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            contentDescription = country.name,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(country.imageUrl)
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                country.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Ranking FIFA 2026: ${country.ranking}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

        }
    }
}