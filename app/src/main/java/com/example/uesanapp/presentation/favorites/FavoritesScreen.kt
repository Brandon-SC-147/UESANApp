package com.example.uesanapp.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.local.FavoriteCountry
import com.example.uesanapp.data.local.FavoritesManager
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(navController: NavController) {
    val favorites by FavoritesManager.getAllFavorites().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

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
            Text(
                "⭐ Favoritos",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "${favorites.size} paises guardados",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFBBBBBB),
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tienes paises favoritos aun.\nAbre el menu y ve a Home para agregar algunos.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF888888),
                            fontSize = 16.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(favorites, key = { it.name }) { favorite ->
                        FavoriteCard(
                            favorite = favorite,
                            onRemoveClick = {
                                scope.launch {
                                    FavoritesManager.delete(favorite)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteCard(favorite: FavoriteCountry, onRemoveClick: () -> Unit) {
    val medalColor = when (favorite.ranking) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color(0xFF4A90E2)
    }

    val gradientColors = when (favorite.ranking) {
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
                            "#${favorite.ranking}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = if (favorite.ranking <= 3) Color.Black else Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Surface(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(6.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        contentDescription = favorite.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(favorite.imageUrl)
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        favorite.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Ranking FIFA: ${favorite.ranking}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFBBBBBB),
                            fontSize = 13.sp
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FF4444), CircleShape)
                        .clickable { onRemoveClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✕",
                        fontSize = 18.sp,
                        color = Color(0xFFFF6666)
                    )
                }
            }
        }
    }
}
