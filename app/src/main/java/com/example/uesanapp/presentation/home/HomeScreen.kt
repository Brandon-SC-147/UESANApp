package com.example.uesanapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.model.CountryModel
import kotlinx.coroutines.delay

val mockCountries = listOf(
    CountryModel("Argentina", 1, "https://flagcdn.com/w320/ar.png"),
    CountryModel("España", 2, "https://flagcdn.com/w320/es.png"),
    CountryModel("Francia", 3, "https://flagcdn.com/w320/fr.png"),
    CountryModel("Colombia", 5, "https://flagcdn.com/w320/co.png"),
    CountryModel("Portugal", 7, "https://flagcdn.com/w320/pt.png"),
    CountryModel("Brasil", 8, "https://flagcdn.com/w320/br.png"),
    CountryModel("Japón", 10, "https://flagcdn.com/w320/jp.png"),
    CountryModel("Perú", 50, "https://flagcdn.com/w320/pe.png"),
)

@Composable
fun HomeScreen(navController: NavController) {
    val visibleItems = remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(Unit) {
        mockCountries.forEachIndexed { index, _ ->
            delay((index * 100).toLong())
            visibleItems.value = visibleItems.value + index
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con trofeo y controles
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                color = Color(0xFF0F3460),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón Atrás
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { navController.popBackStack() }
                            .shadow(4.dp, CircleShape),
                        color = Color(0xFF16213E),
                        shape = CircleShape
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("←", fontSize = 20.sp, color = Color.White)
                        }
                    }

                    // Título y Subtítulo
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "🏆",
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Column {
                            Text(
                                "Ranking FIFA 2026",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = Color.White
                                )
                            )
                            Text(
                                "Posiciones Actuales",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFBBBBBB),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    // Botón Menú
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { }
                            .shadow(4.dp, CircleShape),
                        color = Color(0xFF16213E),
                        shape = CircleShape
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("⋮", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(mockCountries) { index, country ->
                    AnimatedVisibility(
                        visible = visibleItems.value.contains(index),
                        enter = expandVertically(animationSpec = tween(500)) + fadeIn(animationSpec = tween(500))
                    ) {
                        CountryRankingCard(
                            country = country,
                            ranking = country.ranking
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountryRankingCard(country: CountryModel, ranking: Int) {
    val medalColor = when (ranking) {
        1 -> Color(0xFFFFD700)  // Gold
        2 -> Color(0xFFC0C0C0)  // Silver
        3 -> Color(0xFFCD7F32)  // Bronze
        else -> Color(0xFF4A90E2) // Blue
    }

    val gradientColors = when (ranking) {
        1 -> listOf(Color(0xFFFFD700), Color(0xFFFFA500))
        2 -> listOf(Color(0xFFC0C0C0), Color(0xFF808080))
        3 -> listOf(Color(0xFFCD7F32), Color(0xFF8B4513))
        else -> listOf(Color(0xFF0F3460), Color(0xFF16213E))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .shadow(12.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F3460)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = gradientColors,
                        endX = 200f
                    ),
                    alpha = 0.15f
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Ranking Number en círculo
                Surface(
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(6.dp, CircleShape),
                    color = medalColor,
                    shape = CircleShape
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            "#$ranking",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = if (ranking <= 3) Color.Black else Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                // Bandera del país
                Surface(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(6.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        contentDescription = country.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(country.imageUrl)
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                // Información del país
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        country.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Ranking FIFA: ${country.ranking}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFBBBBBB),
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    // Barra de progreso
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(Color(0xFF2A2A3E), RoundedCornerShape(3.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth((country.ranking.toFloat() / 100f).coerceAtMost(1f))
                                .height(6.dp)
                                .background(medalColor, RoundedCornerShape(3.dp))
                        )
                    }
                }

                // Icono de trofeo para los top 3
                if (ranking <= 3) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        when (ranking) {
                            1 -> "🥇"
                            2 -> "🥈"
                            else -> "🥉"
                        },
                        fontSize = 24.sp
                    )
                }
            }

        }
    }
}